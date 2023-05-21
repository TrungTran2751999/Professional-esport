package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.AvartarDTO;
import com.cg.domain.esport.dto.OrganizerDTO;
import com.cg.domain.esport.dto.OrganizerResSecurity;
import com.cg.domain.esport.dto.OrganizerResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "organizers")
public class Organizer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nick_name",length = 100, nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String email;

    @Column(name="phone_number",nullable = false)
    private String phoneNumber;

    @OneToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public OrganizerDTO toOrganizerDTO(){
        return new OrganizerDTO()
                .setId(id)
                .setNickName(nickName);
    }
    public OrganizerResponseDTO toOrganizerResponseDTO(AvartarDTO avartarDTO){
        return new OrganizerResponseDTO()
                .setId(id)
                .setAvartarDTO(avartarDTO)
                .setNickName(nickName);
    }
    public OrganizerResSecurity toOrganizerResSecurity(AvartarDTO avartarDTO){
        return new OrganizerResSecurity()
                .setId(id)
                .setAvartarDTO(avartarDTO)
                .setNickName(nickName)
                .setEmail(email)
                .setPhoneNumber(phoneNumber);
    }

}
