package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Course;
import com.nechyporchuk.studentsapi.entities.CourseDto;
import com.nechyporchuk.studentsapi.exceptions.CourseNotFoundException;
import com.nechyporchuk.studentsapi.mappers.CourseMapper;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final Validator validator;


    @Autowired
    public CourseController(CourseRepository courseRepository, CourseMapper courseMapper, Validator validator) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.validator = validator;
    }

    @GetMapping("{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        Course course = courseRepository
                .findById(id)
                .orElseThrow(() -> new CourseNotFoundException(id));
        return courseMapper.toDto(course);
    }

    @GetMapping
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.listToDto(courses);
    }

    @PostMapping
    public CourseDto createCourse(@Valid @RequestBody CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @PutMapping
    public CourseDto updateCourse(@RequestBody CourseDto courseDto) {
        if (courseDto.id() == null) {
            throw new CourseNotFoundException("Invalid course id");
        }
        Course course = courseRepository
                .findById(courseDto.id())
                .orElseThrow(() -> new CourseNotFoundException(courseDto.id()));
        courseMapper.partialUpdate(courseDto, course);

        Set<ConstraintViolation<Course>> validationResult = validator.validate(course);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        return courseMapper.toDto(courseRepository.save(course));
    }

    @DeleteMapping("{id}")
    public void deleteCourseById(@PathVariable Long id) {
        if (courseRepository.existsById(id)) {
            courseRepository.deleteById(id);
        } else {
            throw new CourseNotFoundException(id);
        }
    }
}
