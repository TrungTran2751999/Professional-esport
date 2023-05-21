package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Role;
import com.cg.domain.esport.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartnerDTO {
    private Long id;
    @NotEmpty(message = "Tên cộng sự không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Tên đăng nhập không đúng định dạng")
    @Size(min = 5, max = 30, message = "Tên phải nhỏ hơn 5 hoặc 30 kí tự")
    private String username;

    @NotEmpty(message = "Mật khẩu không được để trống")
    private String password;

    public User toUser(Role role){
        return new User()
                    .setId(id)
                    .setUsername(username)
                    .setPassword(password)
                    .setRole(role);

    }
}
