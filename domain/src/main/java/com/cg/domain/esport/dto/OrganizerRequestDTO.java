package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Organizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerRequestDTO {
    private Long id;

    @NotNull(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Tên đăng nhập không đúng định dạng")
    @Size(min = 5, max = 30, message = "Tên phải nhỏ hơn 5 hoặc 30 kí tự")
    private String name;

    @NotNull(message = "Tên không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Password không được để trống")
    private String password;

    @NotNull(message = "Tên không được để trống")
    @Pattern(regexp = "^[A-Za-z]*$", message = "Tên đăng nhập không đúng định dạng")
    @Size(min = 5, max = 30, message = "Tên phải nhỏ hơn 5 hoặc 30 kí tự")
    private String nickName;

    @NotNull(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "Tên đăng nhập không đúng định dạng")
    private String phone;

    public Organizer toOrganizer(){
        return new Organizer()
                .setId(id)
                .setEmail(email)
                .setNickName(nickName)
                .setPhoneNumber(phone);
    }
}
