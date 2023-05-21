package com.cg.service.esport.avartar;

import com.cg.domain.esport.entities.Avartar;
import com.cg.domain.esport.entities.Organizer;
import com.cg.repository.esport.AvartarRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AvartarServiceImp implements IAvartarService{
    @Autowired
    private AvartarRespository avartarRespository;

    @Override
    public List<Avartar> findAll() {
        return avartarRespository.findAll();
    }

    @Override
    public Avartar getById(String id) {
        return avartarRespository.getById(id);
    }

    @Override
    public Optional<Avartar> findById(String id) {
        return avartarRespository.findById(id);
    }

    @Override
    public Avartar save(Avartar avartar) {
        return avartarRespository.save(avartar);
    }

    @Override
    public void remove(String id) {
        avartarRespository.deleteById(id);
    }

    @Override
    public void removeByOrganizer(Avartar avartar) {
        avartarRespository.delete(avartar);
    }

    @Override
    public Avartar findByOrganizer(Organizer organizer) {
        return avartarRespository.findByOrganizer(organizer);
    }
}
