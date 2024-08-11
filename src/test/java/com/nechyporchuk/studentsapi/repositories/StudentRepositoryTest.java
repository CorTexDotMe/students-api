package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("john.doe@example.com");
        student.setPhoneNumber("123-456-7890");
        student.setYearOfStudy(2);
    }

    @Test
    public void saveStudentTest() {
        Student savedStudent = studentRepository.save(student);

        assertNotNull(savedStudent.getId());
        assertEquals(student.getFirstName(), savedStudent.getFirstName());
        assertEquals(student.getLastName(), savedStudent.getLastName());
        assertEquals(student.getEmail(), savedStudent.getEmail());
        assertEquals(student.getPhoneNumber(), savedStudent.getPhoneNumber());
        assertEquals(student.getYearOfStudy(), savedStudent.getYearOfStudy());
    }

    @Test
    public void findByIdTest() {
        Student savedStudent = studentRepository.save(student);
        Student foundStudent = studentRepository.findById(savedStudent.getId()).orElse(null);

        assertNotNull(foundStudent);
        assertNotNull(foundStudent.getId());
        assertEquals(savedStudent.getId(), foundStudent.getId());
        assertEquals(savedStudent.getFirstName(), foundStudent.getFirstName());
        assertEquals(savedStudent.getLastName(), foundStudent.getLastName());
        assertEquals(savedStudent.getEmail(), foundStudent.getEmail());
        assertEquals(savedStudent.getPhoneNumber(), foundStudent.getPhoneNumber());
        assertEquals(savedStudent.getYearOfStudy(), foundStudent.getYearOfStudy());
    }

    @Test
    public void updateStudentTest() {
        Student savedStudent = studentRepository.save(student);
        savedStudent.setFirstName("Jane");
        savedStudent.setLastName("Smith");
        savedStudent.setEmail("jane.smith@example.com");
        savedStudent.setPhoneNumber("234-567-8901");
        savedStudent.setYearOfStudy(3);
        studentRepository.save(savedStudent);

        Student updatedStudent = studentRepository.findById(savedStudent.getId()).orElse(null);

        assertNotNull(updatedStudent);
        assertNotNull(updatedStudent.getId());
        assertEquals(savedStudent.getFirstName(), updatedStudent.getFirstName());
        assertEquals(savedStudent.getLastName(), updatedStudent.getLastName());
        assertEquals(savedStudent.getEmail(), updatedStudent.getEmail());
        assertEquals(savedStudent.getPhoneNumber(), updatedStudent.getPhoneNumber());
        assertEquals(savedStudent.getYearOfStudy(), updatedStudent.getYearOfStudy());
    }

    @Test
    public void deleteStudentTest() {
        Student savedStudent = studentRepository.save(student);
        studentRepository.deleteById(savedStudent.getId());

        assertTrue(studentRepository.findById(savedStudent.getId()).isEmpty());
    }
}