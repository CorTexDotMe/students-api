package com.nechyporchuk.studentsapi.controllers;

import com.nechyporchuk.studentsapi.entities.Course;
import com.nechyporchuk.studentsapi.entities.CourseDto;
import com.nechyporchuk.studentsapi.mappers.CourseMapper;
import com.nechyporchuk.studentsapi.repositories.CourseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CourseController(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @GetMapping("{id}")
    public CourseDto getCourseById(@PathVariable Long id) {
        Course course = courseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return courseMapper.toDto(course);
    }

    @GetMapping
    public List<CourseDto> getAll() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.listToDto(courses);
    }

    @PostMapping
    public CourseDto createCourse(@RequestBody CourseDto courseDto) {
        Course course = courseMapper.toEntity(courseDto);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @PutMapping
    public CourseDto updateCourse(@RequestBody CourseDto courseDto) {
        Course course = courseRepository.findById(courseDto.id()).orElseThrow(EntityNotFoundException::new);
        courseMapper.partialUpdate(courseDto, course);
        return courseMapper.toDto(courseRepository.save(course));
    }

    @DeleteMapping("{id}")
    public void deleteCourseById(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
}
