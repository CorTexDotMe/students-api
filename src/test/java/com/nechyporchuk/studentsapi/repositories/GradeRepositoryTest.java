package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Course;
import com.nechyporchuk.studentsapi.entities.Grade;
import com.nechyporchuk.studentsapi.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GradeRepositoryTest {
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    private Student student;
    private Course course;
    private Grade grade;


    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhoneNumber("123-456-7890");
        student.setYearOfStudy(2);
        student = studentRepository.save(student);

        course = new Course();
        course.setName("Introduction to Computer Science");
        course.setDescription("Basic principles of computer science");
        course.setCredits(3);
        course.setInstructorName("Dr. Alice Johnson");
        course = courseRepository.save(course);

        grade = new Grade();
        grade.setGradeValue(85.5);
        grade.setStudent(student);
        grade.setCourse(course);
    }

    @Test
    public void saveGradeTest() {
        Grade savedGrade = gradeRepository.save(grade);

        assertNotNull(savedGrade.getId());
        assertEquals( grade.getGradeValue(), savedGrade.getGradeValue());
        assertEquals(student, savedGrade.getStudent());
        assertEquals(course, savedGrade.getCourse());
    }

    @Test
    public void findByIdTest() {
        Grade savedGrade = gradeRepository.save(grade);
        Grade foundGrade = gradeRepository.findById(savedGrade.getId()).orElse(null);

        assertNotNull(foundGrade);
        assertNotNull(foundGrade.getId());
        assertEquals(savedGrade.getGradeValue(), foundGrade.getGradeValue());
        assertEquals(savedGrade.getStudent(), foundGrade.getStudent());
        assertEquals(savedGrade.getCourse(), foundGrade.getCourse());
    }

    @Test
    public void getAllGradesTest() {
        Grade grade1 = new Grade();
        grade1.setGradeValue(85.5);
        grade1.setStudent(student);
        grade1.setCourse(course);
        gradeRepository.save(grade1);

        Grade grade2 = new Grade();
        grade2.setGradeValue(90.0);
        grade2.setStudent(student);
        grade2.setCourse(course);
        gradeRepository.save(grade2);

        List<Grade> grades = gradeRepository.findAll();

        assertEquals(2, grades.size());
        assertTrue(grades.contains(grade1));
        assertTrue(grades.contains(grade2));
    }

    @Test
    public void updateGradeTest() {
        Grade savedGrade = gradeRepository.save(grade);
        savedGrade.setGradeValue(90.0);
        Grade updatedGrade = gradeRepository.save(savedGrade);

        assertNotNull(updatedGrade.getId());
        assertEquals(savedGrade.getGradeValue(), updatedGrade.getGradeValue());
    }

    @Test
    public void deleteGradeTest() {
        Grade savedGrade = gradeRepository.save(grade);
        Long gradeId = savedGrade.getId();

        gradeRepository.deleteById(gradeId);

        assertTrue(gradeRepository.findById(gradeId).isEmpty());
    }

    @Test
    void averageGradeByStudentTest() {
        Grade grade1 = new Grade();
        grade1.setGradeValue(75.0);
        grade1.setStudent(student);
        grade1.setCourse(course);
        gradeRepository.save(grade1);

        Grade grade2 = new Grade();
        grade2.setGradeValue(79.0);
        grade2.setStudent(student);
        grade2.setCourse(course);
        gradeRepository.save(grade2);

        Grade grade3 = new Grade();
        grade3.setGradeValue(85.0);
        grade3.setStudent(student);
        grade3.setCourse(course);
        gradeRepository.save(grade3);
        double expectedAverageGrade = (grade1.getGradeValue() + grade2.getGradeValue() + grade3.getGradeValue()) / 3;

        Double averageGrade = gradeRepository.averageGradeByStudent(student.getId());
        assertNotNull(averageGrade);
        assertEquals(expectedAverageGrade, averageGrade, 0.01);
    }
}