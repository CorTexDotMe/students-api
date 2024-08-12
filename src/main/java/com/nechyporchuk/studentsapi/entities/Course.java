package com.nechyporchuk.studentsapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "course_id")
    private Long id;

    @NotBlank(message = "Course name is mandatory")
    @Column(name = "course_name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "credits")
    private int credits;

    @Column(name = "instructor_name")
    private String instructorName;

    @OneToMany(mappedBy = "course")
    private List<Grade> grades;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
