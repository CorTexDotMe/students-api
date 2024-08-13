package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.exceptions.CourseNotFoundException;
import com.nechyporchuk.studentsapi.exceptions.GradeNotFoundException;
import com.nechyporchuk.studentsapi.exceptions.StudentNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            fieldErrors.put(fieldName, errorMessage);
        });

        return handleException(HttpStatus.BAD_REQUEST, fieldErrors);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        String errorMessage = "Some fields have constraints(Unique, primary or foreign key). You failed to follow them";
        return handleException(HttpStatus.CONFLICT, errorMessage);

    }

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCourseNotFoundException(CourseNotFoundException ex) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStudentNotFoundException(StudentNotFoundException ex) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(GradeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleGradeNotFoundException(GradeNotFoundException ex) {
        return handleException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> handleException(HttpStatus status, Object message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", new Date());
        errorResponse.put("status", status.value());
        errorResponse.put("error", message);

        return new ResponseEntity<>(errorResponse, status);
    }

}
