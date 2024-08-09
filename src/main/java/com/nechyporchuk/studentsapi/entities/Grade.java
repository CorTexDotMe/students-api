package com.nechyporchuk.studentsapi.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    @JoinColumn
    private Student student;

    private double gradeValue;
}
