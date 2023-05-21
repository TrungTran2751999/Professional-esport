package com.cg.domain.esport.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "team_student")
@TypeDef(name="joinPresent", typeClass = JsonType.class)
public class TeamStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name="student_id")
    private Student student;

    @ManyToOne(targetEntity = TeamTournament.class)
    @JoinColumn(name="team_tournament_id")
    private TeamTournament teamTournament;
}
