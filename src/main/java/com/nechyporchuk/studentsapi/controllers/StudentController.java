package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Student;
import com.nechyporchuk.studentsapi.entities.StudentDto;
import com.nechyporchuk.studentsapi.mappers.StudentMapper;
import com.nechyporchuk.studentsapi.repositories.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
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
        Student student = studentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return studentMapper.toDto(student);
    }

    @GetMapping
    public List<StudentDto> getAll() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.listToDto(students);
    }

    @PostMapping
    public StudentDto createStudent(@RequestBody StudentDto studentDto) {
        Student student = studentMapper.toEntity(studentDto);
        return studentMapper.toDto(studentRepository.save(student));
    }

    @PutMapping
    public StudentDto updateStudent(@RequestBody StudentDto studentDto) {
        Student student = studentRepository.findById(studentDto.id()).orElseThrow(EntityNotFoundException::new);
        studentMapper.partialUpdate(studentDto, student);
        return studentMapper.toDto(studentRepository.save(student));
    }

    @DeleteMapping("{id}")
    public void deleteStudentById(@PathVariable Long id) {
        studentRepository.deleteById(id);
    }
}
