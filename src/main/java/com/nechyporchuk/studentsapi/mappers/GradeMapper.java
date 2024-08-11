package com.nechyporchuk.studentsapi.mappers;

import com.nechyporchuk.studentsapi.entities.Grade;
import com.nechyporchuk.studentsapi.entities.GradeDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GradeMapper {
    Grade toEntity(GradeDto gradeDto);

    GradeDto toDto(Grade grade);

    List<GradeDto> listToDto(List<Grade> grades);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Grade partialUpdate(GradeDto gradeDto, @MappingTarget Grade grade);
}