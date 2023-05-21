package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.AvartarDTO;
import com.cg.domain.esport.dto.StudentDTO;
import com.cg.domain.esport.dto.StudentResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "students")
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "decimal(12,0) default 0")
    private BigDecimal balance;

    public StudentDTO toStudentDTO(){
        return new StudentDTO()
                .setName(name)
                .setId(id)
                .setEmail(email)
                .setBalance(balance)
                .setPassword(password);
    }
    public StudentResponseDTO toStudentResponseDTO(AvartarDTO avartarDTO){
        return new StudentResponseDTO()
                .setId(id)
                .setName(name)
                .setEmail(email)
                .setAvartarDTO(avartarDTO);
    }
}
