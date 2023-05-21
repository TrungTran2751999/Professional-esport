package com.cg.service.esport.mentor;

import com.cg.domain.esport.dto.MentorDTO;
import com.cg.domain.esport.entities.Mentor;
import com.cg.service.IGeneralService;
import org.springframework.transaction.annotation.Transactional;

public interface IMentorService extends IGeneralService<Mentor> {
    MentorDTO createMentor(MentorDTO mentor);

}
