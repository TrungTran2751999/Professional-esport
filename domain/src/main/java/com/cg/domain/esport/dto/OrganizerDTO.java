package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Organizer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerDTO {
    private Long id;
    @NotBlank(message = "Tên không được để trống")
    @Size(min = 5, max = 100, message = "Tên phải nhỏ hơn 5 hoặc 100 kí tự")
    private String nickName;

    private Long userId;
    private String code;

    @NotNull(message = "Ảnh đại diện không được để trống")
    private MultipartFile multipartFile;

    public Organizer toOrganizer(){
        return new Organizer()
                .setId(id)
                .setNickName(nickName);
    }

}
