package com.yashpz.examination_system.examination_system.mappers;


import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.College;

import java.util.List;

public class StudentProfileMapper {

    public static StudentProfile toEntity(StudentProfileRequestDTO requestDTO) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setFullName(requestDTO.getFullName());
        studentProfile.setBranch(requestDTO.getBranch());
        studentProfile.setPhone(requestDTO.getPhone());
        studentProfile.setPassout(requestDTO.getPassout());
        return studentProfile;
    }

    public static List<StudentProfile> toEntity(List<StudentProfileRequestDTO> requestDTOs) {
        return requestDTOs.stream().map(StudentProfileMapper::toEntity).toList();
    }

    public static StudentProfileResponseDTO toResponseDTO(StudentProfile studentProfile) {
        College college = studentProfile.getCollege();
        return new StudentProfileResponseDTO(
                studentProfile.getId(),
                studentProfile.getUser().getId(),
                studentProfile.getFullName(),
                new CollegeDTO(college.getId(), college.getName()),
                studentProfile.getBranch(),
                studentProfile.getPhone(),
                studentProfile.getPassout(),
                studentProfile.getCreatedAt(),
                studentProfile.getUpdatedAt()
        );
    }

    public static List<StudentProfileResponseDTO> toResponseDTO(List<StudentProfile> studentProfiles) {
        return studentProfiles.stream().map(StudentProfileMapper::toResponseDTO).toList();
    }

    public static StudentProfile updateEntity(StudentProfile studentProfile, StudentProfileRequestDTO requestDTO) {
        studentProfile.setFullName(requestDTO.getFullName());
        studentProfile.setBranch(requestDTO.getBranch());
        studentProfile.setPhone(requestDTO.getPhone());
        studentProfile.setPassout(requestDTO.getPassout());
        return studentProfile;
    }
}
