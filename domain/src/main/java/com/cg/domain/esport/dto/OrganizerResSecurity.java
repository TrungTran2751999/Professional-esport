package com.cg.domain.esport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrganizerResSecurity {
    private Long id;
    private String name;
    private String email;
    private String nickName;
    private String phoneNumber;
    private AvartarDTO avartarDTO;
}
