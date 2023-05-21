package com.cg.domain.esport.entities;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tournament")
@TypeDef(name="joinPresent", typeClass = JsonType.class)
public class Tournament{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "prize", nullable = false)
    private BigDecimal prize;

    @Column(name = "join_limit", nullable = false)
    private Integer joinLimit;

    @Type(type = "joinPresent")
    @Column(name = "join_present", nullable = false, columnDefinition = "JSON")
    private List<Integer> joinPresent = new ArrayList<>();

    @ManyToOne(targetEntity = ProcessTornament.class)
    @JoinColumn(name = "process_id")
    private ProcessTornament process;

    @ManyToOne(targetEntity = Organizer.class)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;
}
