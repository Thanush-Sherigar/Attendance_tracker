package com.attendance.server.repository;

import com.attendance.server.model.AttendanceRecord;
import com.attendance.server.model.Course;
import com.attendance.server.model.Student;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRecordRepository extends JpaRepository<AttendanceRecord, Long> {
    // Custom query methods can go here later!
    List<AttendanceRecord> findByStudentEmail(String email);
boolean existsByStudentAndCourse(Student student, Course course);
}