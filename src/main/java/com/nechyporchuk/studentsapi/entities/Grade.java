package com.nechyporchuk.studentsapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "grades")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "grade_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Min(value = 0, message = "Field grade_value must be greater than or equal to 0")
    @Max(value = 100, message = "Field grade_value must be less than or equal to 100")
    @NotNull(message = "Field grade_value is mandatory")
    @Column(name = "grade_value")
    private Double gradeValue;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Grade grade)) return false;
        return id.equals(grade.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
