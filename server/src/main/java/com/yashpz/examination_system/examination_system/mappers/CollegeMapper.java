package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import com.yashpz.examination_system.examination_system.model.College;

import java.util.List;
import java.util.stream.Collectors;

public class CollegeMapper {

    public static College toEntity(CollegeDTO collegeDTO) {
        College college = new College();
        college.setName(collegeDTO.getName());
        return college;
    }

    public static CollegeDTO toDTO(College college) {
        return new CollegeDTO(college.getId(), college.getName());
    }

    public static List<CollegeDTO> toDTO(List<College> colleges) {
        return colleges.stream()
                .map(CollegeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static College updateEntity(College college, CollegeDTO collegeDTO) {
        college.setName(collegeDTO.getName());
        return college;
    }
}
