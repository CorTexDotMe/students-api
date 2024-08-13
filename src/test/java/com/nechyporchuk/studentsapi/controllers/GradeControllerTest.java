package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Course;
import com.nechyporchuk.studentsapi.entities.Grade;
import com.nechyporchuk.studentsapi.entities.Student;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
import com.nechyporchuk.studentsapi.repositories.GradeRepository;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GradeControllerTest {

    @LocalServerPort
    public int serverPort;

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    private Grade grade;
    private Student student;
    private Course course;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;

        student = Student.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123-456-7890")
                .yearOfStudy(2)
                .build();
        student = studentRepository.save(student);

        course = Course.builder()
                .name("Introduction to Computer Science")
                .description("Basic principles of computer science")
                .credits(3)
                .instructorName("Dr. Alice Johnson")
                .build();
        course = courseRepository.save(course);

        grade = Grade.builder()
                .gradeValue(85.5)
                .student(student)
                .course(course)
                .build();
    }

    @AfterEach
    void tearDown() {
        gradeRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    public void getGradeById() {
        grade = gradeRepository.save(grade);

        given()
                .when()
                .get("/grades/" + grade.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("grade_value", equalTo(grade.getGradeValue().floatValue()));
    }

    @Test
    public void getGradeByNonExistentId() {
        given()
                .when()
                .get("/grades/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createGrade() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("course_name", grade.getCourse().getName());
        jsonAsMap.put("grade_value", grade.getGradeValue());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("grade_value", equalTo(grade.getGradeValue().floatValue()));
    }

    @Test
    public void createGradeWithoutStudentId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("course_name", grade.getCourse().getName());
        jsonAsMap.put("grade_value", grade.getGradeValue());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createGradeWithoutCourseName() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("grade_value", grade.getGradeValue());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createGradeWithoutGradeValue() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("course_name", grade.getCourse().getName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createBlankCourseNameGrade() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("course_name", "");
        jsonAsMap.put("grade_value", grade.getGradeValue());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createNegativeGradeValue() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("course_name", grade.getCourse().getName());
        jsonAsMap.put("grade_value", -1);

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createMoreThatHundredGradeValue() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("student_id", grade.getStudent().getId());
        jsonAsMap.put("course_name", grade.getCourse().getName());
        jsonAsMap.put("grade_value", 101);

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/grades")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateGradeById() {
        grade = gradeRepository.save(grade);
        float newGradeValue = 91;

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", grade.getId());
        jsonAsMap.put("grade_value", newGradeValue);

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/grades")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("grade_value", equalTo(newGradeValue));
    }

    @Test
    public void updateGradeByNonExistentId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", 1000);
        jsonAsMap.put("grade_value", grade.getGradeValue());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/grades")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteGradeById() {
        grade = gradeRepository.save(grade);

        assertTrue(gradeRepository.existsById(grade.getId()));

        given()
                .when()
                .delete("/grades/" + grade.getId())
                .then()
                .statusCode(HttpStatus.OK.value());

        assertFalse(gradeRepository.existsById(grade.getId()));
    }

    @Test
    public void deleteGradeByNonExistentId() {
        given()
                .when()
                .delete("/grades/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}