package com.nechyporchuk.studentsapi.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class GradeNotFoundException extends EntityNotFoundException {
    public GradeNotFoundException(String message) {
        super(message);
    }

    public GradeNotFoundException(Long id) {
        super("Grade not found with id: " + id);
    }
}
