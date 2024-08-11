package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * DTO for {@link Grade}
 */
public record GradeDto(
        Long id,
        CourseDto course,
        StudentDto student,
        @JsonProperty("grade_value") double gradeValue
) implements Serializable {
}

