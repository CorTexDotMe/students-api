package com.nechyporchuk.studentsapi.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }

    public CourseNotFoundException(Long id) {
        super("Course not found with id: " + id);
    }
}
