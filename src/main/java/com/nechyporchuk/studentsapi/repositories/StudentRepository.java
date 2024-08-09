package com.nechyporchuk.studentsapi.repositories;

import com.nechyporchuk.studentsapi.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
