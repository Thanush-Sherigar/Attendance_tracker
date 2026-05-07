package com.attendance.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.attendance.server.model.Course;
import com.attendance.server.model.Student;
import com.attendance.server.repository.CourseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
     @Autowired
    private CourseRepository courseRepository;
    public Course saveCourse(Course course){
        Optional<Course> existingCourse=courseRepository.findByCourseName(course.getName());
        if(existingCourse.isPresent()) {
            throw new RuntimeException("course" + course.getName() + "already exists");
        }
            return courseRepository.save(course);
        }
        public Course getCourseById(Long id){
        return courseRepository.findById(id).orElseThrow(()->new RuntimeException("The id"+id+" not found"));
        }
        public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    public void deleteCourse() {
        courseRepository.deleteAll();   
    }
}
