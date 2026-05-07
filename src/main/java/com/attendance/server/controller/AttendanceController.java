package com.attendance.server.controller;

import com.attendance.server.model.AttendanceSession;
import com.attendance.server.repository.SessionRepository;
import com.attendance.server.repository.CourseRepository;
import com.attendance.server.model.Course;
import com.attendance.server.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private CourseRepository courseRepository;

    // 1. TEACHER SIDE: Generate the 5-minute QR Token
    @PostMapping("/generate")
    public String generateQr(@RequestParam Long courseId) {
        String randomToken = UUID.randomUUID().toString();

        // Save the session to the database with the current timestamp
        AttendanceSession session = new AttendanceSession(randomToken, courseId);
        sessionRepository.save(session);

        return randomToken;
    }

    // 2. STUDENT SIDE: Scan the QR Token
    @PostMapping("/mark")
    public String mark(@RequestParam String token) {
        // Find out WHO is scanning based on their JWT Passport!
        String studentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        // 1. First, find the Session using the token
        // AttendanceSession session = sessionRepository.findByToken(token)
        //         .orElseThrow(() -> new RuntimeException("Invalid Token: This QR code does not exist."));
        // // 3. Now that we know the session is valid, get the Course
        // Course course = courseRepository.findById(session.getCourseId())
        //         .orElseThrow(() -> new RuntimeException("Course not found: The ID linked to this QR is invalid."));
        // // Check the 5-minute rule in the Service
        String result = attendanceService.markAttendance(token, studentEmail);
        return result + " (Scanned by: " + studentEmail + ")";
    }
}