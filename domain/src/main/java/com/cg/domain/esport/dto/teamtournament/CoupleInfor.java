package com.cg.domain.esport.dto.teamtournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class CoupleInfor {
    private List<TeamInfo> couple;
    private Date startAt;
}
