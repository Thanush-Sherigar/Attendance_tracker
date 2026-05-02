package com.attendance.server.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="attendance_sessions")
public class AttendanceSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false,unique=true)
    private String token;
    private Long courseId;
    private LocalDateTime createdAt;
    public AttendanceSession(){
    }
    public AttendanceSession(String token,Long courseId){
        this.token=token;
        this.courseId=courseId;
        this.createdAt= LocalDateTime.now();
    }
    public Long getId(){
        return id;
    }
    public String getToken(){
        return token;
    }
    public Long getCourseId(){
        return courseId;
    }
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

}
