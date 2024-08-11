package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

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
        @JsonProperty("year_of_study") int yearOfStudy
) implements Serializable {
}
