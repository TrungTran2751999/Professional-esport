package com.cg.domain.esport.entities;

import com.cg.domain.esport.dto.AvartarDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "avartar")
public class Avartar extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "cloud_id")
    private String cloudId;

    @Column(name = "file_folder")
    private String fileFolder;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_url")
    private String fileUrl;

    @ManyToOne(targetEntity = Student.class)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(targetEntity = Mentor.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "mentor_id")
    private Mentor mentor;

    @ManyToOne(targetEntity = Organizer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    private Organizer organizer;

    @ManyToOne(targetEntity = Category.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(targetEntity = TeamTournament.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "team_tournament")
    private TeamTournament teamTournament;

    public AvartarDTO toAvartarDTO(){
        return new AvartarDTO()
                .setId(id)
                .setFileUrl(fileUrl);
    }

}
