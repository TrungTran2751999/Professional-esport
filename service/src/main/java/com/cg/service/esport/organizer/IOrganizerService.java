package com.cg.service.esport.organizer;

import com.cg.domain.esport.dto.*;
import com.cg.domain.esport.entities.Organizer;
import com.cg.service.IGeneralService;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface IOrganizerService extends IGeneralService<Organizer> {

    OrganizerResponseDTO createOrganizer(OrganizerRequestDTO organizerDTO);
    OrganizerResponseDTO updateAvartar(OrganizerDTO organizerDTO);

    OrganizerResponseDTO updateOrganizerNoAvarTar(OrganizerUpdateDTO organizerDTO);
    OrganizerResponseDTO findByUserId(Long id);
    OrganizerResSecurity findByCodeSecurity(String code);
    void deleteOrganizer(Long id);
    List<OrganizerResSecurity> findByDeleted(Boolean deleted);
    void setDeleted(Boolean deleted, Long id);
}
