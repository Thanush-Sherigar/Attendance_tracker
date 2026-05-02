package com.attendance.server.repository;

import com.attendance.server.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<AttendanceSession,Long> {
    Optional<AttendanceSession> findByToken(String Token);
    void deleteAllByCreatedAt(LocalDateTime fiveMinutes);
}
