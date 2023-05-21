package com.cg.api.esport;

import com.cg.domain.esport.dto.*;
import com.cg.domain.esport.entities.JwtResponse;
import com.cg.domain.esport.entities.Role;
import com.cg.domain.esport.entities.User;
import com.cg.exception.DataInputException;
import com.cg.exception.EmailExistsException;
import com.cg.exception.UnauthorizedException;
import com.cg.service.email.EmailSender;
import com.cg.service.esport.jwt.JwtService;
import com.cg.service.esport.mentor.IMentorService;
import com.cg.service.esport.organizer.IOrganizerService;
import com.cg.service.esport.role.IRoleService;
import com.cg.service.esport.student.IStudentService;
import com.cg.service.esport.user.IUserService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthAPI {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMentorService mentorService;

    @Autowired
    private IOrganizerService organizerService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return appUtils.mapErrorToResponse(bindingResult);
        User user = userService.getByUsername(userLoginDTO.getUsername());
        if(user==null) throw new DataInputException("Sai tài khoản hoặc mật khẩu");
        if(user.getDeleted()){
            throw new UnauthorizedException("Tài khoản và mật khẩu chưa được xác thực");
        }
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            User currentUser = userService.getByUsername(userLoginDTO.getUsername());

//            if (currentUser.getIsFirstLogin()) {
//                DataInputException dataInputException = new DataInputException("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
//                return new ResponseEntity<>(dataInputException, HttpStatus.UNAUTHORIZED);
//            }

//            Optional<StaffDTO>  staffDTOOptional = staffService.getByUsernameDTO(currentUser.getUsername());
//            StaffDTO staff = staffDTOOptional.get();

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    currentUser.getId(),
                    userDetails.getUsername(),
                    currentUser.getUsername(),
                    userDetails.getAuthorities(),
                    currentUser.getCodeSecurity()
            );

            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
                    .httpOnly(false)
                    .secure(false)
                    .path("/")
                    .maxAge(1000 * 60 * 60)
                    .domain("localhost")
                    .build();

            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
                    .body(jwtResponse);

        } catch (Exception e) {
            e.printStackTrace();
            throw new UnauthorizedException("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
        }
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/register/partner")
    public ResponseEntity<?> register(@Validated @RequestBody PartnerDTO partnerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        User partner = userService.getByUsername(partnerDTO.getUsername());

        if (partner != null) {
            throw new EmailExistsException("Tên đăng nhập đã tồn tại trong hệ thống.");
        }

        Role role = roleService.getById(2L);

        User user = partnerDTO.toUser(role);

        user.setId(null);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User partnerResult = userService.save(user);

        return new ResponseEntity<>(partnerResult.toPartnerDTO().setPassword(null), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/register/mentor")
    public ResponseEntity<?> registerMentor(@Validated @RequestBody MentorDTO mentorDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        User mentor = userService.getByUsername(mentorDTO.getUsername());

        if (mentor != null) {
            throw new EmailExistsException("Tên đăng nhập đã tồn tại trong hệ thống.");
        }
        mentorDTO.setPassword(passwordEncoder.encode(mentorDTO.getPassword()));
        MentorDTO mentorResult = mentorService.createMentor(mentorDTO);

        return new ResponseEntity<>(mentorResult.setPassword(null), HttpStatus.CREATED);
    }
    @PostMapping("/register/organizer")
    public ResponseEntity<?> registerOrganizer(@Validated OrganizerRequestDTO organizerDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        User user = userService.getByUsername(organizerDTO.getName());

        if (user != null) {
            throw new EmailExistsException("Tên đăng nhập đã tồn tại trong hệ thống.");
        }
        organizerDTO.setPassword(passwordEncoder.encode(organizerDTO.getPassword()));
        OrganizerResponseDTO organizerResult = organizerService.createOrganizer(organizerDTO);
        return new ResponseEntity<>(organizerResult, HttpStatus.CREATED);
    }
    @PostMapping("/register/student")
    public ResponseEntity<?> registerStudent(@Validated @RequestBody StudentDTO studentDTO, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return appUtils.mapErrorToResponse(bindingResult);
        }

        User user = userService.getByUsername(studentDTO.getName());

        if (user != null) {
            throw new EmailExistsException("Tên đăng nhập đã tồn tại trong hệ thống.");
        }
        studentDTO.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        StudentResponseDTO studentResult = studentService.createStudent(studentDTO);

        return new ResponseEntity<>(studentResult, HttpStatus.CREATED);
    }
}
