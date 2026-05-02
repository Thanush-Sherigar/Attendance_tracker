package com.attendance.server.service;

import com.attendance.server.model.AttendanceSession;
import com.attendance.server.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AttendanceService {
@Autowired
private SessionRepository sessionRepository;
    @Scheduled(fixedRate=3600000)
    public void cleanUpExpiredToken(){
        LocalDateTime fiveMinutes=LocalDateTime.now().minusMinutes(5);
        sessionRepository.deleteAllByCreatedAt(fiveMinutes);
        System.out.println("Cleared teh expired tokens");
    }
    public String markAttendance(String scannedToken){
        AttendanceSession session= sessionRepository.findByToken(scannedToken).orElseThrow(()-> new RuntimeException("Invalid token"));
        LocalDateTime now= LocalDateTime.now();
        Duration duration= Duration.between(session.getCreatedAt(),now);
        long minutesElapsed= duration.toMinutes();
        if(minutesElapsed>1){
            return "This token is expired";
        }
        return "Success: attendance marked";
    }
    public String generateAttendanceToken() {
        return UUID.randomUUID().toString();
    }
}
