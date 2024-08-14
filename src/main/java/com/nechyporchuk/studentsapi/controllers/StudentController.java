package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Student;
import com.nechyporchuk.studentsapi.entities.StudentDto;
import com.nechyporchuk.studentsapi.exceptions.StudentNotFoundException;
import com.nechyporchuk.studentsapi.mappers.StudentMapper;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final Validator validator;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper, Validator validator) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.validator = validator;
    }

    @GetMapping("{id}")
    public StudentDto getStudentById(@PathVariable Long id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return studentMapper.toDto(student);
    }

    @GetMapping
    public List<StudentDto> getAll() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.listToDto(students);
    }

    @PostMapping
    public StudentDto createStudent(@Valid @RequestBody StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    @PutMapping
    public StudentDto updateStudent(@RequestBody StudentDto studentDto) {
        if (studentDto.id() == null) {
            throw new StudentNotFoundException("Invalid student id");
        }
        Student student = studentRepository
                .findById(studentDto.id())
                .orElseThrow(() -> new StudentNotFoundException(studentDto.id()));
        studentMapper.partialUpdate(studentDto, student);

        Set<ConstraintViolation<Student>> validationResult = validator.validate(student);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        return studentMapper.toDto(studentRepository.save(student));
    }

    @DeleteMapping("{id}")
    public void deleteStudentById(@PathVariable Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
        } else {
            throw new StudentNotFoundException(id);
        }
    }
}
