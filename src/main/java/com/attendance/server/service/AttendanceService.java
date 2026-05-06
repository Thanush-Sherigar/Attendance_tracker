package com.attendance.server.service;

import com.attendance.server.model.AttendanceRecord;
import com.attendance.server.model.AttendanceSession;
import com.attendance.server.model.Course;
import com.attendance.server.model.Student;
import com.attendance.server.repository.AttendanceRecordRepository;
import com.attendance.server.repository.CourseRepository;
import com.attendance.server.repository.SessionRepository;
import com.attendance.server.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AttendanceService {
@Autowired
private AttendanceRecordRepository attendanceRepository;

@Autowired
private StudentRepository studentRepository;

@Autowired
private CourseRepository courseRepository;

@Autowired
private SessionRepository sessionRepository;
    @Scheduled(fixedRate=3600000)
    public void cleanUpExpiredToken(){
        LocalDateTime fiveMinutes=LocalDateTime.now().minusMinutes(5);
        sessionRepository.deleteAllByCreatedAt(fiveMinutes);
        System.out.println("Cleared teh expired tokens");
    }
    public String markAttendance(String scannedToken,String studentEmail, Course course){
        AttendanceSession session= sessionRepository.findByToken(scannedToken).orElseThrow(()-> new RuntimeException("Invalid token"));
        LocalDateTime now= LocalDateTime.now();
        Duration duration= Duration.between(session.getCreatedAt(),now);
        long minutesElapsed= duration.toMinutes();
        if(minutesElapsed>1){
            return "This token is expired";
        }
        Student student = studentRepository.findByEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("Student not found"));
// We get the Course using the ID stored in the session
        Course sessionCourse = courseRepository.findById(session.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // 4. Duplicate Check
        if (attendanceRepository.existsByStudentAndCourse(student, sessionCourse)) {
            return "ALREADY_MARKED";
        }

        // 5. Save the final record
        AttendanceRecord record = new AttendanceRecord(student, sessionCourse, LocalDateTime.now());
        attendanceRepository.save(record);
        
        return "SUCCESS";
    }
    public String generateAttendanceToken() {
        return UUID.randomUUID().toString();
    }
}
