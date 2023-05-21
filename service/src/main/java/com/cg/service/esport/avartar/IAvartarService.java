package com.cg.service.esport.avartar;

import com.cg.domain.esport.entities.Avartar;
import com.cg.domain.esport.entities.Organizer;
import com.cg.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IAvartarService {
    List<Avartar> findAll();
    Avartar getById(String id);
    Optional<Avartar> findById(String id);
    Avartar save(Avartar avartar);
    void remove(String id);
    void removeByOrganizer(Avartar avartar);
    Avartar findByOrganizer(Organizer organizer);
}
