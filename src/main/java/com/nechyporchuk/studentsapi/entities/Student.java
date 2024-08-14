package com.nechyporchuk.studentsapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "student_id")
    private Long id;

    @NotBlank(message = "Student first_name is mandatory")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "Student last_name is mandatory")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Invalid email address")
    @NotBlank(message = "Student email is mandatory")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Positive(message = "Student year_of_study has to be a positive number")
    @Column(name = "year_of_study")
    private Integer yearOfStudy;

    @OneToMany(mappedBy = "student")
    private List<Grade> grades;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
