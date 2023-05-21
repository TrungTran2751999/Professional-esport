package com.cg.domain.esport.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import com.cg.domain.esport.dto.teamtournament.CoupleTournament;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "history_team_tournament")
@TypeDef(name="history", typeClass = JsonType.class)
public class HistoryTeamTournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = TeamTournament.class)
    @JoinColumn(name="team_tournament")
    private TeamTournament teamTournament;

    @ManyToOne(targetEntity = Tournament.class)
    @JoinColumn(name="tournament")
    private Tournament tournament;

    @Type(type = "history")
    @Column(name="history", columnDefinition = "JSON")
    private CoupleTournament history;

}
