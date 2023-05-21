package com.cg.repository.esport;

import com.cg.domain.esport.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByEmail(String email);
    List<Student> findByNameAndEmail(String name, String password);
}
