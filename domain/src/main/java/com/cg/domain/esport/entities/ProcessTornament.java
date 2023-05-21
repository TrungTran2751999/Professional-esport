package com.cg.domain.esport.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "process_tournament")
public class ProcessTornament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "register_finish")
    private Date registerFinish;

    @Column(name = "confirm_finish")
    private Date confirmFinish;

    @Column(name = "start_finish")
    private Date startFinish;

    @Column(name = "end_finish")
    private Date endFinish;

    @ManyToOne(targetEntity = Tournament.class)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

}
