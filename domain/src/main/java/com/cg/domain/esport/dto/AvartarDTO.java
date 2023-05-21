package com.cg.domain.esport.dto;

import com.cg.domain.esport.entities.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AvartarDTO {
    private String id;
    private String cloudId;
    private String fileFolder;
    private String fileName;
    private String fileType;
    private String fileUrl;
    public Avartar toAvartarOrganizer(Organizer organizer){
        return new Avartar()
                .setId(id)
                .setCloudId(cloudId)
                .setFileType(fileType)
                .setFileUrl(fileUrl)
                .setOrganizer(organizer);
    }
}
