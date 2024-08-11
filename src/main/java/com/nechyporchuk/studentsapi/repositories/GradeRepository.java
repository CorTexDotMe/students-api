package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("SELECT AVG(gradeValue) FROM Grade WHERE student.id = ?1")
    Double averageGradeByStudent(Long studentId);
}
