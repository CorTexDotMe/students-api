package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
