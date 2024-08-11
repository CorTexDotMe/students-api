package com.nechyporchuk.studentsapi.entities;

import java.io.Serializable;

/**
 * DTO for {@link Grade}
 */
public record GradeDto(
        Long id,
        CourseDto course,
        StudentDto student,
        double gradeValue
) implements Serializable {
}

