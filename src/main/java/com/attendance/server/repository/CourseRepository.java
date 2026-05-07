package com.attendance.server.repository;
import com.attendance.server.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // Custom query methods can go here later!
    Optional<Course> findByCourseName(String courseName);
}