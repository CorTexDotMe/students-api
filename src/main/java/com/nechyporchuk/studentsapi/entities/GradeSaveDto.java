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
        @NotNull(message = "Field student_id is mandatory")
        Long studentId,

        @NotBlank(message = "Field course_name is mandatory")
        @JsonProperty("course_name")
        String courseName,

        @Min(value = 0, message = "Field grade_value must be greater than or equal to 0")
        @Max(value = 100, message = "Field grade_value must be less than or equal to 100")
        @NotNull(message = "Field grade_value is mandatory")
        @JsonProperty("grade_value")
        Double gradeValue
) implements Serializable {

}
