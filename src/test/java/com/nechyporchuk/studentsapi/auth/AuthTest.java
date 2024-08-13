package com.nechyporchuk.studentsapi.auth;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    @LocalServerPort
    public int serverPort;

    @Value("${spring.security.user.name}")
    public String username;

    @Value("${spring.security.user.password}")
    public String password;

    @BeforeEach
    public void setUp() {
        RestAssured.port = serverPort;
    }

    @Test
    public void testUnauthorized() {
        given()
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void testAuthorized() {
        given()
                .auth()
                .basic(username, password)
                .when()
                .get("/")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
