//package com.cg.api;
//
//import com.cg.domain.dto.staff.StaffCreateDTO;
//import com.cg.domain.dto.staff.StaffDTO;
//import com.cg.domain.dto.user.UserForgetPasswordDTO;
//import com.cg.domain.dto.user.UserLoginDTO;
//import com.cg.domain.entity.*;
//import com.cg.exception.DataInputException;
//import com.cg.exception.EmailExistsException;
//import com.cg.exception.UnauthorizedException;
//import com.cg.service.email.EmailSender;
//import com.cg.service.jwt.JwtService;
//import com.cg.service.otp.IOtpService;
//import com.cg.service.role.IRoleService;
//import com.cg.service.staff.IStaffService;
//import com.cg.service.user.IUserService;
//import com.cg.utils.AppUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseCookie;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.validation.Valid;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthAPI {
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JwtService jwtService;
//
//    @Autowired
//    private AppUtils appUtils;
//
//    @Autowired
//    private IOtpService otpService;
//
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private IRoleService roleService;
//
//    @Autowired
//    private IStaffService staffService;
//
//    @Autowired
//    private EmailSender emailSender;
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult bindingResult) {
//
//        if (bindingResult.hasErrors())
//            return appUtils.mapErrorToResponse(bindingResult);
//
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userLoginDTO.getUsername(), userLoginDTO.getPassword()));
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            String jwt = jwtService.generateTokenLogin(authentication);
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            User currentUser = userService.getByUsername(userLoginDTO.getUsername());
//
//            if(currentUser.getDeleted()) {
//                DataInputException dataInputException = new DataInputException("Tài khoản của bạn đã bị khóa!");
//                return new ResponseEntity<>(dataInputException, HttpStatus.UNAUTHORIZED);
//            }
//
//            if (currentUser.getIsFirstLogin()) {
//                DataInputException dataInputException = new DataInputException("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
//                return new ResponseEntity<>(dataInputException, HttpStatus.UNAUTHORIZED);
//            }
//
//            Optional<StaffDTO>  staffDTOOptional = staffService.getByUsernameDTO(currentUser.getUsername());
//            StaffDTO staff = staffDTOOptional.get();
//
//            JwtResponse jwtResponse = new JwtResponse(
//                    jwt,
//                    currentUser.getId(),
//                    userDetails.getUsername(),
//                    staff.getFullName(),
//                    userDetails.getAuthorities()
//            );
//
//            ResponseCookie springCookie = ResponseCookie.from("JWT", jwt)
//                    .httpOnly(false)
//                    .secure(false)
//                    .path("/")
//                    .maxAge(1000 * 60 * 60)
//                    .domain("localhost")
//                    .build();
//
//            return ResponseEntity
//                    .ok()
//                    .header(HttpHeaders.SET_COOKIE, springCookie.toString())
//                    .body(jwtResponse);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new UnauthorizedException("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
//        }
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<?> register(@Validated @RequestBody StaffCreateDTO staffCreateDTO, BindingResult bindingResult) {
//        if (bindingResult.hasFieldErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//
//        Optional<User> userOptional = userService.findByUserName(staffCreateDTO.getUsername());
//
//        if (userOptional.isPresent()) {
//            throw new EmailExistsException("Email đã tồn tại trong hệ thống.");
//        }
//
//        Optional<Staff> staffOptional = staffService.findByPhone(staffCreateDTO.getPhone());
//
//        if (staffOptional.isPresent()) {
//            throw new DataInputException("Số điện thoại đã tồn tại trong hệ thống.");
//        }
//
//        LocationRegion locationRegion = staffCreateDTO.toLocationRegion();
//        locationRegion.setId(null);
//
//        Optional<Role> optRole = roleService.findById(Long.parseLong(staffCreateDTO.getRoleId()));
//
//        if (!optRole.isPresent()) {
//            throw new DataInputException("Role không hợp lệ.");
//        }
//
//        Role role = optRole.get();
//
//        User user = staffCreateDTO.toUser(role);
//
//        user.setId(null);
//        user.setIsFirstLogin(true);
//
//        user.setCodeFirstLogin(appUtils.randomOtp(12));
//
//        user.setPassword(appUtils.randomPassword(6));
//
//        Staff newStaff = staffService.createNoAvatar(staffCreateDTO, locationRegion, user);
//
//        emailSender.sendRegisterStaffEmail(newStaff, user.getUsername());
//
//        return new ResponseEntity<>(newStaff.toStaffDTO(), HttpStatus.CREATED);
//    }
//
//    @PostMapping("/forget-password")
//    public ResponseEntity<?> sendOtp(@Valid @RequestBody UserForgetPasswordDTO userForgetPasswordDTO, BindingResult bindingResult) {
//
//        if (bindingResult.hasFieldErrors()) {
//            return appUtils.mapErrorToResponse(bindingResult);
//        }
//
//        String username = userForgetPasswordDTO.getUsername();
//        String password = userForgetPasswordDTO.getPassword();
//        String passwordConfirm = userForgetPasswordDTO.getPasswordConfirm();
//        String otpCode = userForgetPasswordDTO.getOtp();
//
//        User user = userService.getByUsername(username);
//
//        if (user == null) {
//            throw new DataInputException("Email không hợp lệ!");
//        }
//
//        if (user.getDeleted()){
//            throw new DataInputException("Tài khoản của bạn đã bị khóa!");
//        }
//
//        if(!password.equals(passwordConfirm)){
//            throw new DataInputException("Mật khẩu không khớp nhau vui lòng kiểm tra lại!");
//        }
//
//        Otp otp = otpService.getByCode(otpCode);
//
//        if (otp == null) {
//            throw new DataInputException("OTP không hợp lệ");
//        }
//
//        otpService.softDelete(otp.getId());
//
//        user.setPassword(password);
//
//        userService.save(user);
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//}
