package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.Category;
import com.cg.domain.esport.entities.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeamTournamentDTO {
    private Long id;
    private String name;
    private CategoryDTO category;
    private StudentDTO leader;
}
