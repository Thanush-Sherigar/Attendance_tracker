package com.attendance.server.controller;

import com.attendance.server.model.Course;
import com.attendance.server.service.CourseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/course")

public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public Course CreateCourse(@Valid @RequestBody Course course) {
        return courseService.saveCourse(course);
    }
    @DeleteMapping("/delete")
    public void deleteCourse(){
        courseService.deleteCourse();
    }
    @GetMapping("courses")
    public List<Course> getAllCourses(){
        return courseService.getAllCourses();
    }
}
