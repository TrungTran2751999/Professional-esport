package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.MentorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "mentor")
public class Mentor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    public MentorDTO toMentorDTO(){
        return new MentorDTO()
                .setId(id)
                .setPassword(password)
                .setEmail(email)
                .setUsername(name)
                .setCategoryDTO(category.toCategoryDTO());
    }
}
