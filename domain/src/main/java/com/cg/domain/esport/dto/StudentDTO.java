package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;

    @NotBlank(message = "Tên không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Tên đăng nhập không đúng định dạng")
    @Length(min = 5, max = 50, message = "Độ dài tên phải nhỏ hơn 50 và lớn hơn 5 kí tự")
    private String name;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Length(min = 10, max = 50, message = "Độ dài mật khẩu phải nhỏ hơn 50 kí tự và lớn hơn 10 kí tự")
    private String password;

    @NotBlank(message = "Email không đuợc để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private BigDecimal balance;
    @NotNull
    private MultipartFile avartar;

    public Student toStudent(){
        return new Student()
                .setName(name)
                .setId(id)
                .setEmail(email)
                .setBalance(balance)
                .setPassword(password);
    }
}
