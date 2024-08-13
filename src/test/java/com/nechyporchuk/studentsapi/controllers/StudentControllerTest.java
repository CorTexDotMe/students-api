package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Student;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
class StudentControllerTest {

    @LocalServerPort
    public int serverPort;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;


    @Autowired
    private StudentRepository studentRepository;
    private Student student;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;

        student = Student.builder()
                .firstName("Michael")
                .lastName("Brown")
                .email("michael.brown@example.com")
                .phoneNumber("456-789-0123")
                .yearOfStudy(4)
                .build();
    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    public void getStudentById() {
        student = studentRepository.save(student);

        given()
                .auth()
                .basic(username, password)
                .when()
                .get("/students/" + student.getId())
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo(student.getFirstName()))
                .body("last_name", equalTo(student.getLastName()))
                .body("email", equalTo(student.getEmail()))
                .body("phone_number", equalTo(student.getPhoneNumber()))
                .body("year_of_study", equalTo(student.getYearOfStudy()));
    }

    @Test
    public void getStudentByNonExistentId() {
        given()
                .auth()
                .basic(username, password)
                .when()
                .get("/students/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void createStudent() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo(student.getFirstName()))
                .body("last_name", equalTo(student.getLastName()))
                .body("email", equalTo(student.getEmail()))
                .body("phone_number", equalTo(student.getPhoneNumber()))
                .body("year_of_study", equalTo(student.getYearOfStudy()));
    }

    @Test
    public void createStudentMandatoryFields() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo(student.getFirstName()))
                .body("last_name", equalTo(student.getLastName()))
                .body("email", equalTo(student.getEmail()));
    }

    @Test
    public void createStudentWithoutEmail() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createStudentWithoutFirstName() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createStudentWithoutLastName() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createBlankFirstNameStudent() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", "");
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createBlankLastNameStudent() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("last_name", "");
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createWrongEmailStudent() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", "example.of.wrong@");
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void createNotUniqueEmailStudent() {
        studentRepository.save(student);

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", student.getYearOfStudy());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    public void createNegativeYearOfStudyStudent() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());
        jsonAsMap.put("phone_number", student.getPhoneNumber());
        jsonAsMap.put("year_of_study", -1);

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .post("/students")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void updateStudentById() {
        student = studentRepository.save(student);
        String newFirstName = "John";
        String newLastName = "Smith";
        String newEmail = "john.smith@example.com";

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", student.getId());
        jsonAsMap.put("first_name", newFirstName);
        jsonAsMap.put("last_name", newLastName);
        jsonAsMap.put("email", newEmail);

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/students")
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("first_name", equalTo(newFirstName))
                .body("last_name", equalTo(newLastName))
                .body("email", equalTo(newEmail));
    }

    @Test
    public void updateStudentByNonExistentId() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("id", 1000);
        jsonAsMap.put("first_name", student.getFirstName());
        jsonAsMap.put("last_name", student.getLastName());
        jsonAsMap.put("email", student.getEmail());

        given()
                .auth()
                .basic(username, password)
                .contentType(ContentType.JSON)
                .body(jsonAsMap)
                .when()
                .put("/students")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deleteStudentById() {
        student = studentRepository.save(student);

        assertTrue(studentRepository.existsById(student.getId()));

        given()
                .auth()
                .basic(username, password)
                .when()
                .delete("/students/" + student.getId())
                .then()
                .statusCode(HttpStatus.OK.value());

        assertFalse(studentRepository.existsById(student.getId()));
    }

    @Test
    public void deleteStudentByNonExistentId() {
        given()
                .auth()
                .basic(username, password)
                .when()
                .delete("/students/" + 1000)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}