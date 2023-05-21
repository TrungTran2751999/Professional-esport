package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Category;
import com.cg.domain.esport.entities.Mentor;
import com.cg.domain.esport.entities.Role;
import com.cg.domain.esport.entities.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MentorDTO {
    private Long id;
    @NotNull(message = "Tên đăng nhập không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "Tên đăng nhập không đúng định dạng")
    @Size(min = 5, max = 30, message = "Tên phải nhỏ hơn 5 hoặc 30 kí tự")
    private String username;

    @NotNull(message = "Password không được để trống")
    private String password;

    @NotNull(message = "Tên không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    private CategoryDTO categoryDTO;

    private Long categoryId;

    public User toUser(Role role){
        return new User()
                .setId(id)
                .setUsername(email)
                .setPassword(password)
                .setRole(role);
    }
    public Mentor toMentor(){
        return new Mentor()
                    .setId(id)
                    .setPassword(password)
                    .setEmail(email)
                    .setName(username)
                    .setCategory(categoryDTO.toCategory());
    }
}
