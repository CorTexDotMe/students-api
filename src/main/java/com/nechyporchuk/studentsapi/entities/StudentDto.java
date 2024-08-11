package com.nechyporchuk.studentsapi.entities;

import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
public record StudentDto(
        Long id,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        int yearOfStudy
) implements Serializable {
}
