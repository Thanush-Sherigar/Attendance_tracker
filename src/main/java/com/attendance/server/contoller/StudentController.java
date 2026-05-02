package com.attendance.server.contoller;

import com.attendance.server.service.AttendanceService;
import com.attendance.server.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.attendance.server.model.Student;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private AttendanceService attendanceService;
    @PostMapping
    public Student createStudent(@Valid @RequestBody Student student){
        return studentService.saveStudent(student);
    }
    @GetMapping
    public List<Student> getStudents(){
return studentService.getAllStudents();
    }
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id){
        return studentService.getStudentById(id);
    }
    @GetMapping("/attendance")
    public String getMarkAttendance(){
        String token= attendanceService.generateAttendanceToken();
        return attendanceService.markAttendance(token);
    }
}
