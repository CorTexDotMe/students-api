package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.*;
import com.nechyporchuk.studentsapi.exceptions.CourseNotFoundException;
import com.nechyporchuk.studentsapi.exceptions.GradeNotFoundException;
import com.nechyporchuk.studentsapi.exceptions.StudentNotFoundException;
import com.nechyporchuk.studentsapi.mappers.GradeMapper;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
import com.nechyporchuk.studentsapi.repositories.GradeRepository;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/grades")
public class GradeController {

    private final GradeRepository gradeRepository;
    private final GradeMapper gradeMapper;
    private final Validator validator;

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public GradeController(GradeRepository gradeRepository, GradeMapper gradeMapper, Validator validator,
                           CourseRepository courseRepository, StudentRepository studentRepository) {
        this.gradeRepository = gradeRepository;
        this.gradeMapper = gradeMapper;
        this.validator = validator;
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @GetMapping("{id}")
    public GradeDto getGradeById(@PathVariable Long id) {
        Grade grade = gradeRepository
                .findById(id)
                .orElseThrow(() -> new GradeNotFoundException(id));
        return gradeMapper.toDto(grade);
    }

    @GetMapping
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
    public GradeDto createGrade(@Valid @RequestBody GradeSaveDto gradeDto) {
        Student student = studentRepository
                .findById(gradeDto.studentId())
                .orElseThrow(() -> new StudentNotFoundException(gradeDto.studentId()));
        Course course = courseRepository
                .findByName(gradeDto.courseName())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with name: " + gradeDto.courseName()));

        Grade grade = Grade.builder()
                .student(student)
                .course(course)
                .gradeValue(gradeDto.gradeValue())
                .build();
        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @PutMapping
    public GradeDto updateGrade(@RequestBody GradeSaveDto gradeDto) {
        if (gradeDto.id() == null) {
            throw new GradeNotFoundException("Invalid grade id");
        }
        Grade grade = gradeRepository
                .findById(gradeDto.id())
                .orElseThrow(() -> new GradeNotFoundException(gradeDto.id()));
        gradeMapper.partialUpdate(gradeDto, grade);

        Set<ConstraintViolation<Grade>> validationResult = validator.validate(grade);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        return gradeMapper.toDto(gradeRepository.save(grade));
    }

    @DeleteMapping("{id}")
    public void deleteGradeById(@PathVariable Long id) {
        if (gradeRepository.existsById(id)) {
            gradeRepository.deleteById(id);
        } else {
            throw new GradeNotFoundException(id);
        }
    }

    @GetMapping("/average/{student_id}")
    public ResponseEntity<Map<String, Object>> averageGrade(@PathVariable Long student_id) {
        Double averageGrade = gradeRepository.averageGradeByStudent(student_id);

        Map<String, Object> response = new HashMap<>();
        response.put("average", averageGrade);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
