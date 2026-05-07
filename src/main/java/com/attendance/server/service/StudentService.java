package com.attendance.server.service;

import com.attendance.server.model.Student;
import com.attendance.server.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    public Student saveStudent(Student student){
        Optional<Student> existingStudent=studentRepository.findByEmail(student.getEmail());
        if(existingStudent.isPresent()) {
            throw new RuntimeException("user" + student.getName() + "already exists");
        }
            return studentRepository.save(student);
        }
        public Student getStudentById(Long id){
        return studentRepository.findById(id).orElseThrow(()->new RuntimeException("The id"+id+" not found"));
        }
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
