package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * DTO for {@link Grade}
 */
public record GradeDto(
        Long id,

        StudentDto student,

        CourseDto course,

        @Min(0)
        @Max(100)
        @NotNull(message = "Grade value is mandatory")
        @JsonProperty("grade_value") Double gradeValue
) implements Serializable {
}

