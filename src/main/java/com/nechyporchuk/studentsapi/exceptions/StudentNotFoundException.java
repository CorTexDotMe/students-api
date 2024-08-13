package com.nechyporchuk.studentsapi.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }

    public StudentNotFoundException(Long id) {
        super("Student not found with id: " + id);
    }
}
