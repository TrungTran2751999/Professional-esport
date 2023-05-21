package com.cg.service.esport.mentor;

import com.cg.domain.esport.dto.MentorDTO;
import com.cg.domain.esport.entities.Category;
import com.cg.domain.esport.entities.Mentor;
import com.cg.domain.esport.entities.Role;
import com.cg.domain.esport.entities.User;
import com.cg.repository.esport.CategoryRepository;
import com.cg.repository.esport.MentorRepository;
import com.cg.repository.esport.RoleRepository;
import com.cg.repository.esport.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class MentorServiceImp implements IMentorService{
    @Autowired
    private MentorRepository mentorRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Override
    public List<Mentor> findAll() {
        return mentorRepository.findAll();
    }

    @Override
    public Mentor getById(Long id) {
        return mentorRepository.getById(id);
    }

    @Override
    public Optional<Mentor> findById(Long id) {
        return mentorRepository.findById(id);
    }

    @Override
    public Mentor save(Mentor mentor) {
        return mentorRepository.save(mentor);
    }

    @Override
    public void remove(Long id) {
        mentorRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MentorDTO createMentor(MentorDTO mentor) {
        Role role = roleRepository.getById(4L);
        User user = mentor.toUser(role);
        User userResult = userRepository.save(user);
        Category category = categoryRepository.getById(mentor.getCategoryId());
        mentor.setCategoryDTO(category.toCategoryDTO());
        Mentor mentorResult = mentorRepository.save(mentor.toMentor());
        return mentorResult.toMentorDTO();
    }
}
