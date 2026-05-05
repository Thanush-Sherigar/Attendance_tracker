package com.attendance.server.controller;

import com.attendance.server.model.Student;
import com.attendance.server.repository.StudentRepository;
import com.attendance.server.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Spring's built-in password scrambler
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // DTO (Data Transfer Object) for receiving login data
    public static class LoginRequest {
        public String email;
        public String password;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        // 1. Find the student by email
        Optional<Student> studentOpt = studentRepository.findByEmail(request.email);

        if (studentOpt.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        Student student = studentOpt.get();

        // 2. Check if passwords match (In a real app, the DB password would be BCrypt hashed)
        // For testing right now, we will just compare them directly or use encoder.matches()
        if (!passwordEncoder.matches(request.password, student.getPassword())) {
            throw new RuntimeException("Wrong password!");
        }

        // 3. Success! Print the passport (Generate JWT)
        return jwtUtil.generateToken(student.getEmail());
    }
    @PostMapping("/register")
    public String register(@RequestBody Student newStudent) {
        // 1. Check if the email is already taken
        if (studentRepository.findByEmail(newStudent.getEmail()).isPresent()) {
            throw new RuntimeException("Email is already registered!");
        }

        // 2. Scramble (Hash) the password
        String scrambledPassword = passwordEncoder.encode(newStudent.getPassword());
        newStudent.setPassword(scrambledPassword); // Replace plain text with scrambled text

        // 3. Save to database
        studentRepository.save(newStudent);

        // 4. Return a success message
        return "Student registered successfully! You can now login.";
    }
}