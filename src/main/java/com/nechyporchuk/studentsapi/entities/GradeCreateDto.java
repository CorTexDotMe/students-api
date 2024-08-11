package com.nechyporchuk.studentsapi.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public record GradeCreateDto(
        @JsonProperty("student_id") Long studentId,
        @JsonProperty("course_name") String courseName,
        @JsonProperty("grade_value") Double gradeValue
) implements Serializable {

}
