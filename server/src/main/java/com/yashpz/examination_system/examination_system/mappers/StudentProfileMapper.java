package com.yashpz.examination_system.examination_system.mappers;


import com.yashpz.examination_system.examination_system.dto.User.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.User.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.model.User;

public class StudentProfileMapper {

    public static StudentProfile toEntity(StudentProfileRequestDTO requestDTO, User user, College college) {
        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setUser(user);
        studentProfile.setFullName(requestDTO.getFullName());
        studentProfile.setCollege(college);
        studentProfile.setBranch(requestDTO.getBranch());
        studentProfile.setPhone(requestDTO.getPhone());
        studentProfile.setPassout(requestDTO.getPassout());
        return studentProfile;
    }

    public static StudentProfileResponseDTO toResponseDTO(StudentProfile studentProfile) {
        return new StudentProfileResponseDTO(
                studentProfile.getId(),
                studentProfile.getUser().getId(),
                studentProfile.getFullName(),
                studentProfile.getCollege().getName(),
                studentProfile.getBranch(),
                studentProfile.getPhone(),
                studentProfile.getPassout(),
                studentProfile.getCreatedAt(),
                studentProfile.getUpdatedAt()
        );
    }
}
