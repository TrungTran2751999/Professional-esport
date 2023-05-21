package com.cg.controller.esport;

import com.cg.domain.esport.dto.StudentDTO;
import com.cg.domain.esport.dto.StudentResponseDTO;
import com.cg.domain.esport.entities.JwtResponse;
import com.cg.domain.esport.entities.Otp;
import com.cg.domain.esport.entities.Student;
import com.cg.domain.esport.entities.User;
import com.cg.exception.UnauthorizedException;
import com.cg.service.esport.jwt.JwtService;
import com.cg.service.esport.otp.IOtpService;
import com.cg.service.esport.student.IStudentService;
import com.cg.service.esport.user.IUserService;
import com.cg.service.esport.user.UserServiceImp;
import com.cg.utils.GooglePojo;
import com.cg.utils.GoogleUtil;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class LoginController {
    @Autowired
    private GoogleUtil googleUtils;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IStudentService studentService;

    @Autowired
    private IOtpService otpService;


    @RequestMapping("/login-google")
    public String loginGoogle(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return "redirect:/login?google=error";
        }
        String accessToken = googleUtils.getToken(code);

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
//        UserDetails userDetail = googleUtils.buildUser(googlePojo);
//        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null,
//                userDetail.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        try{
            User currentUser = userService.getByUsername(googlePojo.getEmail());
            if(currentUser == null){
                StudentResponseDTO studentDTO = studentService.createStudentByGoogle(googlePojo);
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(googlePojo.getEmail(), googlePojo.getId()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtService.generateTokenLogin(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            Cookie cookie = new Cookie("JWT", jwt);
            response.addCookie(cookie);
            return "redirect:/logingoogle";
        }catch (Exception e){
            e.printStackTrace();
//            throw new UnauthorizedException("Email hoặc mật khẩu không đúng! Vui lòng kiểm tra lại!");
            return "redirect:/error";
        }
    }
    @RequestMapping("/logingoogle")
    public String loginPage(){
        return "logingoogle";
    }
    @RequestMapping("/confirm-user/{id}")
    @Transactional
    public String confirmUser(@PathVariable("id") String id){
        Optional<Otp> otpOtp = otpService.findById(id);
        if(otpOtp.isPresent()){
            User user = otpOtp.get().getUser();
            userService.save((User) user.setDeleted(false));
            otpService.remove(id);
            return "redirect:/login-success";
        }
        return "redirect:/login-fail";
    }
}
