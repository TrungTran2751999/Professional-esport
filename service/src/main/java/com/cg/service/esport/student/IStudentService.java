package com.cg.service.esport.student;

import com.cg.domain.esport.dto.StudentDTO;
import com.cg.domain.esport.dto.StudentResponseDTO;
import com.cg.domain.esport.entities.Student;
import com.cg.service.IGeneralService;
import com.cg.utils.GooglePojo;

public interface IStudentService extends IGeneralService<Student> {
    StudentResponseDTO createStudent(StudentDTO studentDTO);
    StudentResponseDTO createStudentByGoogle(GooglePojo googlePojo);

}
