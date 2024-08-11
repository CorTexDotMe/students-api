package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO for {@link Course}
 */
public record CourseDto(
        Long id,
        String name,
        String description,
        int credits,
        @JsonProperty("instructor_name") String instructorName
) implements Serializable {
}
