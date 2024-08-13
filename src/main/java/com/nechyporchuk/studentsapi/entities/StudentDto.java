package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
public record StudentDto(
        Long id,

        @NotBlank(message = "Student first name is mandatory")
        @JsonProperty("first_name")
        String firstName,

        @NotBlank(message = "Student last name is mandatory")
        @JsonProperty("last_name")
        String lastName,

        @Email(message = "Invalid email address")
        @NotBlank(message = "Student email is mandatory")
        String email,

        @JsonProperty("phone_number")
        String phoneNumber,

        @PositiveOrZero(message = "Student year of study has to be positive number")
        @JsonProperty("year_of_study")
        int yearOfStudy
) implements Serializable {
}
