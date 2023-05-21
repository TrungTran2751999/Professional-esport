package com.cg.domain.esport.dto.teamtournament;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Component
public class CoupleTournament {
    private int count;
    private List<String> name;
    private List<CoupleInfor> team;
}
