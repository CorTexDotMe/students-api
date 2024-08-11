package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.*;
import com.nechyporchuk.studentsapi.mappers.GradeMapper;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
import com.nechyporchuk.studentsapi.repositories.GradeRepository;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grade")
public class GradeController {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GradeController(GradeRepository gradeRepository, GradeMapper gradeMapper,
                           CourseRepository courseRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("{id}")
    public GradeDto getGradeById(@PathVariable Long id) {
        Grade grade = gradeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return gradeMapper.toDto(grade);
    }

    @GetMapping("/all")
    public List<GradeDto> getAll() {
        List<Grade> grades = gradeRepository.findAll();
        return gradeMapper.listToDto(grades);
    }

    @GetMapping("/all/{student_id}")
    public List<GradeDto> getAll(@PathVariable Long student_id) {
        List<Grade> grades = gradeRepository.findByStudentId(student_id);
        return gradeMapper.listToDto(grades);
    }

    @PostMapping
    public GradeDto createGrade(@RequestBody GradeCreateDto gradeDto) {
        Student student = studentRepository.findById(gradeDto.studentId()).orElseThrow(EntityNotFoundException::new);
        Course course = courseRepository.findByName(gradeDto.courseName()).orElseThrow(EntityNotFoundException::new);

        Grade grade = Grade.builder()
                .student(student)
                .course(course)
                .gradeValue(gradeDto.gradeValue())
                .build();
        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @PutMapping
    public GradeDto updateGrade(@RequestBody GradeDto gradeDto) {
        Grade grade = gradeRepository.findById(gradeDto.id()).orElseThrow(EntityNotFoundException::new);
        gradeMapper.partialUpdate(gradeDto, grade);
        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @DeleteMapping("{id}")
    public void deleteGradeById(@PathVariable Long id) {
        gradeRepository.deleteById(id);
    }

    @GetMapping("/average/{student_id}")
    public ResponseEntity<Double> averageGrade(@PathVariable Long student_id) {
        Double averageGrade = gradeRepository.averageGradeByStudent(student_id);
        return new ResponseEntity<>(averageGrade, HttpStatus.OK);
    }
}
