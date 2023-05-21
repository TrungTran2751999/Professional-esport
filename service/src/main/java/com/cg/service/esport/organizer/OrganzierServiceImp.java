package com.cg.service.esport.organizer;

import com.cg.domain.esport.dto.*;
import com.cg.domain.esport.entities.*;
import com.cg.exception.DataInputException;
import com.cg.exception.UnauthorizedException;
import com.cg.repository.esport.OrganizerRepository;
import com.cg.repository.esport.OtpRepository;
import com.cg.repository.esport.RoleRepository;
import com.cg.service.email.EmailSender;
import com.cg.service.email.SendEmailThread;
import com.cg.service.esport.avartar.IAvartarService;
import com.cg.service.esport.jwt.JwtService;
import com.cg.service.esport.securitycode.ISecurityCodeService;
import com.cg.service.esport.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.driver.GoogleDriveConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrganzierServiceImp implements IOrganizerService{
    @Autowired
    private OrganizerRepository organizerRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private IAvartarService avartarService;
    @Autowired
    private IUserService userService;

    @Autowired
    private ISecurityCodeService securityCodeService;
    @Autowired
    private AppUtils appUtils;

    @Autowired
    private SendEmailThread sendEmailThread;
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public List<Organizer> findAll() {

        return organizerRepository.findAll();
    }

    @Override
    public Organizer getById(Long id) {
        return organizerRepository.getById(id);
    }

    @Override
    public Optional<Organizer> findById(Long id) {
        return organizerRepository.findById(id);
    }


    @Override
    public Organizer save(Organizer organizer) {
        return organizerRepository.save(organizer);
    }

    @Override
    public void remove(Long id) {
        organizerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public OrganizerResponseDTO createOrganizer(OrganizerRequestDTO organizerDTO) {
        List<Organizer> organizer = organizerRepository.findByEmail(organizerDTO.getEmail());
        if(organizer.size()==0){
            Role role = roleRepository.getById(5L);
            User user = new User()
                    .setUsername(organizerDTO.getName())
                    .setPassword(organizerDTO.getPassword())
                    .setRole(role);
            user = userService.save(user);
            SecurityCode securityCode = securityCodeService.save(new SecurityCode().setUser(user));
            user.setCodeSecurity(securityCode.getId());
            Organizer organizerResult = organizerRepository.save(organizerDTO.toOrganizer().setUser(user));
            Avartar avartar = new Avartar();
            avartar = avartarService.save(avartar);
            avartar.setOrganizer(organizerResult);
            return organizerResult.toOrganizerResponseDTO(avartar.toAvartarDTO());
        }else{
            throw new DataInputException("Email đã tồn tại");
        }
    }
    @Override
    @Transactional
    public OrganizerResponseDTO updateAvartar(OrganizerDTO organizerDTO) {
        User user = userService.findByCodeSecurity(organizerDTO.getCode());
        if(user != null){
            Organizer organizer = organizerRepository.getByUser(user);
            Avartar avartar = avartarService.findByOrganizer(organizer);
            String fileIdDelete = avartar.getFileUrl();
            avartar.setFileUrl(appUtils.uploadAvartar(organizerDTO.getMultipartFile(),avartar.getId()));
            if(fileIdDelete != null){
                String fileId = fileIdDelete.split("&")[1].split("=")[1];
                try {
                    appUtils.deleteFile(fileId);
                } catch (Exception e) {
                    throw new DataInputException("FAIL");
                }
            }
            return organizer.toOrganizerResponseDTO(avartar.toAvartarDTO());
        }else{
            throw new DataInputException("Nhà tổ chức không tồn tại");
        }
    }

    @Override
    @Transactional
    public OrganizerResponseDTO updateOrganizerNoAvarTar(OrganizerUpdateDTO organizerDTO) {
        List<Organizer> organizerCheckEmail = organizerRepository.findByEmail(organizerDTO.getEmail());
        if(organizerCheckEmail.size() > 0){
            throw new DataInputException("Email đã tồn tại");
        }
        User user = userService.findByCodeSecurity(organizerDTO.getCode());
        if(user != null){
            Organizer organizer = organizerRepository.getByUser(user);
            organizer = organizerDTO.setId(organizer.getId()).toOrganizer().setUser(user);
            organizer = organizerRepository.save(organizer);
            Avartar avartar = avartarService.findByOrganizer(organizer);
            return organizer.toOrganizerResponseDTO(avartar.toAvartarDTO());
        }else{
            throw new DataInputException("Nhà tổ chức không tồn tại");
        }
    }

    @Override
    public OrganizerResponseDTO findByUserId(Long id) {
        Organizer organizer = organizerRepository.getByUserId(id);
        Avartar avartar = avartarService.findByOrganizer(organizer);
        return organizer.toOrganizerResponseDTO(avartar.toAvartarDTO());
    }

    @Override
    public OrganizerResSecurity findByCodeSecurity(String code) {
        User user = userService.findByCodeSecurity(code);
        if(user != null){
            Organizer organizer = organizerRepository.getByUser(user);
            Avartar avartar = avartarService.findByOrganizer(organizer);
            return organizer.toOrganizerResSecurity(avartar.toAvartarDTO());
        }else{
            throw new DataInputException("Nhà tổ chức không tồn tại");
        }
    }

    @Override
    @Transactional
    public void deleteOrganizer(Long id) {
        Optional<Organizer> organizerOpt = organizerRepository.findById(id);
        if(organizerOpt.isPresent()){
            Organizer organizer = organizerOpt.get();
            User user = organizer.getUser();
            Avartar avartar = avartarService.findByOrganizer(organizer);
            SecurityCode code = securityCodeService.findByUser(user);
            if(avartar.getFileUrl()==null && avartar.getFileUrl().equals("")){
                securityCodeService.remove(code.getId());
                avartarService.remove(avartar.getId());
                organizerRepository.delete(organizer);
                userService.remove(user.getId());
            }else{
                throw new DataInputException("Không thể xóa nhà tổ chức sau khi đã kích hoạt");
            }
        }else{
            throw new DataInputException("Nhà tổ chức không tồn tại");
        }
    }

    @Override
    public List<OrganizerResSecurity> findByDeleted(Boolean deleted) {
        return organizerRepository.getByDeleted(deleted)
                .stream()
                .map(organizer -> {
                    Avartar avartar = avartarService.findByOrganizer(organizer);
                    return organizer.toOrganizerResSecurity(avartar.toAvartarDTO());
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setDeleted(Boolean deleted, Long id) {
        Optional<Organizer> organizerOpt = organizerRepository.findById(id);
        if(organizerOpt.isPresent()){
            User user = organizerOpt.get().getUser();
            userService.save((User) user.setDeleted(deleted));
            organizerOpt.get().setDeleted(deleted);
            organizerRepository.save(organizerOpt.get());
            if(!deleted) sendEmailThread.start(user, organizerOpt.get().getEmail());
        }else{
            throw new DataInputException("Nhà tổ chức không tồn tại");
        }
    }


}
