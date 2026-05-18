package com.attendance.server.controller;

import com.attendance.server.model.AttendanceRecord;
import com.attendance.server.model.AttendanceSession;
import com.attendance.server.repository.SessionRepository;
import com.attendance.server.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private SessionRepository sessionRepository;

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
        String result = attendanceService.markAttendance(token, studentEmail);
        return result + " (Scanned by: " + studentEmail + ")";
    }
    @GetMapping("/history")
public ResponseEntity<List<AttendanceRecord>> getStudentHistory() {
    // Get the email of the logged-in student from the Security Context
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    
    List<AttendanceRecord> history = attendanceService.getHistoryByEmail(email);
    return ResponseEntity.ok(history);
}
}