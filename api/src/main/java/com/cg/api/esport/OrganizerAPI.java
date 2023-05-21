package com.cg.api.esport;

import com.cg.domain.esport.dto.*;
import com.cg.domain.esport.entities.Organizer;
import com.cg.domain.esport.entities.User;
import com.cg.exception.DataInputException;
import com.cg.exception.UnauthorizedException;
import com.cg.repository.esport.OrganizerRepository;
import com.cg.service.esport.avartar.IAvartarService;
import com.cg.service.esport.jwt.JwtService;
import com.cg.service.esport.organizer.IOrganizerService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizer")
public class OrganizerAPI {
    @Autowired
    private IOrganizerService organizerService;
    @Autowired
    private IAvartarService avartarService;
    @Autowired
    private AppUtils appUtils;
    @GetMapping
    public ResponseEntity<?> getAll(){
        List<OrganizerResponseDTO> organzier = organizerService.findAll()
                                        .stream()
                                        .map(organizer -> {
                                            AvartarDTO avartarDTO = avartarService.findByOrganizer(organizer).toAvartarDTO();
                                            return organizer.toOrganizerResponseDTO(avartarDTO);
                                        })
                                        .collect(Collectors.toList());
        return new ResponseEntity<>(organzier, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        try{
            Organizer organizer = organizerService.getById(id);
            AvartarDTO avartarDTO = avartarService.findByOrganizer(organizer).toAvartarDTO();
            return new ResponseEntity<>(organizerService.getById(id).toOrganizerResponseDTO(avartarDTO), HttpStatus.OK);
        }catch (Exception e){
            throw new DataInputException("Không tìm thấy nhà tổ chức");
        }
    }
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZER')")
    public ResponseEntity<?> updateOrganizer(@Validated @RequestBody OrganizerUpdateDTO organizerDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
            return new ResponseEntity<>(organizerService.updateOrganizerNoAvarTar(organizerDTO), HttpStatus.OK);
    }
    @GetMapping("/security/{code}")
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZER')")
    public ResponseEntity<?> findSecurity(@PathVariable("code") String code){
        try{
            OrganizerResSecurity organizerResponseDTO = organizerService.findByCodeSecurity(code);
            return new ResponseEntity<>(organizerResponseDTO, HttpStatus.OK);
        }catch (Exception e){
            throw new DataInputException("Không tìm tìm thấy nhà tổ chức");
        }
    }
    @PostMapping("/avartar")
    @PreAuthorize("hasAnyAuthority('ROLE_ORGANIZER')")
    public ResponseEntity<?> updateImage(@Validated OrganizerDTO organizerDTO, BindingResult bindingResult){
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }
        return new ResponseEntity<>(organizerService.updateAvartar(organizerDTO), HttpStatus.OK);
    }
    @GetMapping("/deleted")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> findByDeleted(@RequestParam("deleted") Boolean deleted){
        return new ResponseEntity<>(organizerService.findByDeleted(deleted), HttpStatus.OK);
    }
    @PostMapping("/deleted")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> setDeleted(@RequestParam("deleted") Boolean deleted, @RequestParam("id") Long id){
        organizerService.setDeleted(deleted, id);
        return new ResponseEntity<>("UPDATE SUCCESS", HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteOrganizer(@PathVariable("id") Long id){
        organizerService.deleteOrganizer(id);
        return new ResponseEntity<>("DELETE SUCCESS", HttpStatus.OK);
    }
}
