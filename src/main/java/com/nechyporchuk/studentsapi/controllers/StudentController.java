package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Student;
import com.nechyporchuk.studentsapi.entities.StudentDto;
import com.nechyporchuk.studentsapi.exceptions.StudentNotFoundException;
import com.nechyporchuk.studentsapi.mappers.StudentMapper;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentController(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
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
        Student student = studentRepository
                .findById(studentDto.id())
                .orElseThrow(() -> new StudentNotFoundException(studentDto.id()));
        studentMapper.partialUpdate(studentDto, student);
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
