package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Course;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
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
class CourseControllerTest {

    @LocalServerPort
    public int serverPort;

    @Autowired
    private CourseRepository courseRepository;
    private Course course;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;

        course = Course.builder()
                .name("Course Name. Controller create test")
                .description("Course Description. Controller create test")
                .credits(128)
                .instructorName("Instructor Name. Controller create test")
                .build();
    }

    @AfterEach
    void tearDown() {
        courseRepository.deleteAll();
    }

    @Test
    public void getCourseById() {
        course = courseRepository.save(course);

        given()
                .when()
                .get("/courses/" + course.getId())
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void getCourseByNonExistentId() {
        given()
                .when()
                .get("/courses/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createCourse() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", course.getName());
        jsonAsMap.put("description", course.getDescription());
        jsonAsMap.put("credits", course.getCredits());
        jsonAsMap.put("instructor_name", course.getInstructorName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(course.getName()))
                .body("description", equalTo(course.getDescription()))
                .body("credits", equalTo(course.getCredits()))
                .body("instructor_name", equalTo(course.getInstructorName()));
    }

    @Test
    public void createCourseMandatoryFields() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", course.getName());


        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(course.getName()));
    }

    @Test
    public void createCourseWithoutName() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("description", course.getDescription());
        jsonAsMap.put("credits", course.getCredits());
        jsonAsMap.put("instructorName", course.getInstructorName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createBlankNameCourse() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "");
        jsonAsMap.put("description", course.getDescription());
        jsonAsMap.put("credits", course.getCredits());
        jsonAsMap.put("instructorName", course.getInstructorName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createNotUniqueNameCourse() {
        courseRepository.save(course);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", course.getName());
        jsonAsMap.put("description", course.getDescription());
        jsonAsMap.put("credits", course.getCredits());
        jsonAsMap.put("instructorName", course.getInstructorName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void createNegativeCreditsCourse() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", course.getName());
        jsonAsMap.put("description", course.getDescription());
        jsonAsMap.put("credits", -4);
        jsonAsMap.put("instructorName", course.getInstructorName());

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/courses")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateCourseById() {
        course = courseRepository.save(course);
        String newName = "Absolutely new name";

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", course.getId());
        jsonAsMap.put("name", newName);

        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/courses")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("name", equalTo(newName));
    }

    @Test
    public void updateCourseByNonExistentId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", 1000);
        jsonAsMap.put("name", course.getName());


        given()
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/courses")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteCourseById() {
        course = courseRepository.save(course);

        assertTrue(courseRepository.existsById(course.getId()));

        given()
                .when()
                .delete("/courses/" + course.getId())
                .then()
                .statusCode(HttpStatus.OK.value());

        assertFalse(courseRepository.existsById(course.getId()));
    }

    @Test
    public void deleteCourseByNonExistentId() {
        given()
                .when()
                .delete("/courses/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}