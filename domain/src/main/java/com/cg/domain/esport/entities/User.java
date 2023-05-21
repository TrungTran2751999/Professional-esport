package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.PartnerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false, columnDefinition = "varchar(50)")
    private String username;

    @Column(nullable = false)
    private String password;

    @JoinColumn(name="role_id", nullable = false)
    @ManyToOne(targetEntity = Role.class)
    private Role role;

    @Column(name = "code_security")
    private String codeSecurity;

    public PartnerDTO toPartnerDTO(){
        return new PartnerDTO()
                    .setId(id)
                    .setUsername(username)
                    .setPassword(password);
    }
}
