package com.cg.service.esport.student;

import com.cg.domain.esport.dto.StudentDTO;
import com.cg.domain.esport.dto.StudentResponseDTO;
import com.cg.domain.esport.entities.*;
import com.cg.exception.DataInputException;
import com.cg.repository.esport.RoleRepository;
import com.cg.repository.esport.StudentRepository;
import com.cg.repository.esport.UserRepository;
import com.cg.service.esport.avartar.IAvartarService;
import com.cg.service.esport.user.IUserService;
import com.cg.utils.AppUtils;
import com.cg.utils.GooglePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
@Service
public class StudentServiceImp implements IStudentService{
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IAvartarService avartarService;
    @Autowired
    private AppUtils appUtils;

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.getById(id);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void remove(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentDTO studentDTO) {
        List<Student> studentCheckEmail = studentRepository.findByEmail(studentDTO.getEmail());
        if(studentCheckEmail.size()==0){
            Role role = roleRepository.getById(5L);
            User user = new User()
                    .setUsername(studentDTO.getName())
                    .setPassword(studentDTO.getPassword())
                    .setRole(role);
            userService.save(user);
            Student studentResult = studentRepository.save(studentDTO.toStudent());
            Avartar avartar = new Avartar();
            avartar = avartarService.save(avartar);
            avartar.setFileUrl(appUtils.uploadAvartar(studentDTO.getAvartar(), avartar.getId()));
            avartar.setStudent(studentResult);
            return studentResult.toStudentResponseDTO(avartar.toAvartarDTO());
        }else{
            throw new DataInputException("Email đã tồn tại");
        }
    }

    @Override
    @Transactional
    public StudentResponseDTO createStudentByGoogle(GooglePojo googlePojo) {
        Role role = roleRepository.getById(3L);
        User user = new User()
                .setUsername(googlePojo.getEmail())
                .setPassword(passwordEncoder.encode(googlePojo.getId()))
                .setRole(role);
        user = userService.save(user);
        Student student = new Student()
                                .setEmail(googlePojo.getEmail())
                                .setName(googlePojo.getEmail().split("@")[0])
                                .setPassword(passwordEncoder.encode(googlePojo.getId()))
                                .setBalance(new BigDecimal(0));
        student = studentRepository.save(student);
        Avartar avartar = new Avartar();
        avartar = avartar.setStudent(student);
        return student.toStudentResponseDTO(avartar.toAvartarDTO());
    }

}
