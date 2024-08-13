package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record GradeSaveDto(
        Long id,

        @JsonProperty("student_id")
        @NotNull(message = "Student id is mandatory")
        Long studentId,

        @NotBlank(message = "Course name is mandatory")
        @JsonProperty("course_name")
        String courseName,

        @Min(0)
        @Max(100)
        @NotNull(message = "Grade value is mandatory")
        @JsonProperty("grade_value")
        Double gradeValue
) implements Serializable {

}
