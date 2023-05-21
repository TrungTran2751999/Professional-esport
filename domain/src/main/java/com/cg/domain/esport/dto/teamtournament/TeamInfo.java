package com.cg.domain.esport.dto.teamtournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class TeamInfo {
    private int teamId;
    private int score;
    private boolean status;
}
