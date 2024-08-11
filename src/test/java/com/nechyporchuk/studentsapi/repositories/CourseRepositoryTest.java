package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    private Course course;

    @BeforeEach
    public void setUp() {
        course = Course.builder()
                .name("Introduction to Computer Science")
                .description("Basic principles of computer science")
                .credits(3)
                .instructorName("Dr. Alice Johnson")
                .build();
    }

    @Test
    public void saveCourseTest() {
        Course savedCourse = courseRepository.save(course);

        assertNotNull(savedCourse.getId());
        assertEquals(course.getName(), savedCourse.getName());
        assertEquals(course.getDescription(), savedCourse.getDescription());
        assertEquals(course.getCredits(), savedCourse.getCredits());
        assertEquals(course.getInstructorName(), savedCourse.getInstructorName());
    }

    @Test
    public void findCourseByIdTest() {
        Course savedCourse = courseRepository.save(course);
        Course foundCourse = courseRepository.findById(savedCourse.getId()).orElse(null);

        assertNotNull(foundCourse);
        assertNotNull(foundCourse.getId());
        assertEquals(savedCourse.getName(), foundCourse.getName());
        assertEquals(savedCourse.getDescription(), foundCourse.getDescription());
        assertEquals(savedCourse.getCredits(), foundCourse.getCredits());
        assertEquals(savedCourse.getInstructorName(), foundCourse.getInstructorName());
    }

    @Test
    public void findCourseByNameTest() {
        Course savedCourse = courseRepository.save(course);
        Course foundCourse = courseRepository.findByName(savedCourse.getName()).orElse(null);

        assertNotNull(foundCourse);
        assertNotNull(foundCourse.getId());
        assertEquals(savedCourse.getName(), foundCourse.getName());
        assertEquals(savedCourse.getDescription(), foundCourse.getDescription());
        assertEquals(savedCourse.getCredits(), foundCourse.getCredits());
        assertEquals(savedCourse.getInstructorName(), foundCourse.getInstructorName());
    }

    @Test
    public void findAllCoursesTest() {
        Course course1 = Course.builder()
                .name("Course 1 Name. Find all test")
                .description("Course 1 Description. Find all test")
                .credits(256)
                .instructorName("Instructor Name. Find all test")
                .build();

        Course course2 = Course.builder()
                .name("Course 2 Name. Find all test")
                .description("Course 2 Description. Find all test")
                .credits(512)
                .instructorName("Instructor Name. Find all test")
                .build();

        course1 = courseRepository.save(course1);
        course2 = courseRepository.save(course2);
        List<Course> foundCourses = courseRepository.findAll();

        assertEquals(2, foundCourses.size());
        assertTrue(foundCourses.contains(course1));
        assertTrue(foundCourses.contains(course2));
    }

    @Test
    public void updateCourseByIdTest() {
        Course savedCourse = courseRepository.save(course);
        savedCourse.setName("Updated Course Name. Update test");
        savedCourse.setDescription("Updated Course Description. Update test");
        savedCourse.setCredits(32);
        savedCourse.setInstructorName("Updated Instructor Name. Update test");
        Course updatedCourse = courseRepository.save(savedCourse);

        assertNotNull(updatedCourse.getId());
        assertEquals(savedCourse.getName(), updatedCourse.getName());
        assertEquals(savedCourse.getDescription(), updatedCourse.getDescription());
        assertEquals(savedCourse.getCredits(), updatedCourse.getCredits());
        assertEquals(savedCourse.getInstructorName(), updatedCourse.getInstructorName());
    }

    @Test
    public void deleteCourseByIdTest() {
        Course savedCourse = courseRepository.save(course);
        Long courseId = savedCourse.getId();

        courseRepository.deleteById(courseId);

        assertTrue(courseRepository.findById(courseId).isEmpty());
    }
}