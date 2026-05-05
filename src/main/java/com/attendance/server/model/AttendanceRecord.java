package com.attendance.server.model;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AttendanceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the student who attended
    @ManyToOne
    private Student student;

    // Link to the course they attended
    @ManyToOne
    private Course course;

    // The exact moment they scanned
    private LocalDateTime timestamp;

    // Default constructor for JPA
    public AttendanceRecord() {}

    public AttendanceRecord(Student student, Course course, LocalDateTime timestamp) {
        this.student = student;
        this.course = course;
        this.timestamp = timestamp;
    }

    // Getters and Setters go here...
    public Student getStudent() {return student;}
    public void setStudent(Student student) {this.student=student;}
    public Course getCourse(){return course;}
    public void setCourse(Course Course){this.course=course;}
}