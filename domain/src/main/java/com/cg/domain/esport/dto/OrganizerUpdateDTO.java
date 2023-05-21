package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Organizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizerUpdateDTO {
    private Long id;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Tên không được để trống")
    @Size(min = 5, max = 100, message = "Tên phải nhỏ hơn 5 hoặc 30 kí tự")
    private String nickName;
    @NotBlank(message = "SDT không được để trống")
    @Pattern(regexp = "^0[0-9]{9}$", message = "SDT không đúng định dạng")
    private String phoneNumber;
    private String code;
    public Organizer toOrganizer(){
        return new Organizer()
                .setId(id)
                .setEmail(email)
                .setNickName(nickName)
                .setPhoneNumber(phoneNumber);
    }
}
