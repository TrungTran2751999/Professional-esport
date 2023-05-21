package com.cg.repository.esport;

import com.cg.domain.esport.entities.Avartar;
import com.cg.domain.esport.entities.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvartarRespository extends JpaRepository<Avartar, String> {
    Avartar findByOrganizer(Organizer organizer);
}
