package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

/**
 * DTO for {@link Course}
 */
public record CourseDto(
        Long id,

        @NotBlank(message = "Course name is mandatory")
        String name,

        String description,

        @PositiveOrZero
        int credits,

        @JsonProperty("instructor_name")
        String instructorName
) implements Serializable {
}
