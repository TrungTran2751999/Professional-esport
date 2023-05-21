package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.teamtournament.CoupleTournament;
import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tournament_table")
@TypeDef(name="couple", typeClass = JsonType.class)
public class TournamentTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "couple")
    @Column(name="eliminate", columnDefinition = "JSON")
    private CoupleTournament eliminateRound;

    @Type(type = "couple")
    @Column(name="quarter", columnDefinition = "JSON")
    private CoupleTournament quarterRound;

    @Type(type = "couple")
    @Column(name="semi", columnDefinition = "JSON")
    private CoupleTournament semiRound;

    @Type(type = "couple")
    @Column(name="final", columnDefinition = "JSON")
    private CoupleTournament finalRound;

    @ManyToOne(targetEntity = Tournament.class)
    @JoinColumn(name="tounament_id")
    private Tournament tournament;
}
