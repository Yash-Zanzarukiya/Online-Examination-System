package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.User.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.User.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.StudentProfileMapper;
import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.repository.StudentProfileRepository;
import com.yashpz.examination_system.examination_system.repository.UserRepository;
import com.yashpz.examination_system.examination_system.repository.CollegeRepository;
import com.yashpz.examination_system.examination_system.utils.ResourceAccessUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserRepository userRepository;
    private final CollegeRepository collegeRepository;
    private final ResourceAccessUtil resourceAccessUtil;

    @Autowired
    public StudentProfileService(StudentProfileRepository studentProfileRepository, UserRepository userRepository, CollegeRepository collegeRepository, ResourceAccessUtil resourceAccessUtil) {
        this.studentProfileRepository = studentProfileRepository;
        this.userRepository = userRepository;
        this.collegeRepository = collegeRepository;
        this.resourceAccessUtil = resourceAccessUtil;
    }

    public StudentProfileResponseDTO createStudentProfile(StudentProfileRequestDTO studentProfileDTO) {
        User user = userRepository.findById(studentProfileDTO.getUserId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "User Not Found"));

        College college = collegeRepository.findById(studentProfileDTO.getCollegeId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "College Not Found"));

        StudentProfile existingProfile = studentProfileRepository.findByUserId(user.getId());
        if (existingProfile!=null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Student Profile Already Exists");

        StudentProfile studentProfile = StudentProfileMapper.toEntity(studentProfileDTO, user, college);

        studentProfileRepository.save(studentProfile);

        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO getStudentProfileByUserId(UUID userId) {
        StudentProfile studentProfile = studentProfileRepository.findByUserId(userId);
        if (studentProfile==null)
            throw new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found");

        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO getStudentProfileById(UUID profileId) {
        StudentProfile studentProfile = studentProfileRepository.findById(profileId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found"));
        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO updateStudentProfile(UUID profileId, StudentProfileRequestDTO studentProfileDTO) {
        StudentProfile existingProfile = studentProfileRepository.findById(profileId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found"));

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getId());

        User user = userRepository.findById(studentProfileDTO.getUserId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "User Not Found"));

        College college = collegeRepository.findById(studentProfileDTO.getCollegeId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "College Not Found"));

        existingProfile.setUser(user);
        existingProfile.setFullName(studentProfileDTO.getFullName());
        existingProfile.setCollege(college);
        existingProfile.setBranch(studentProfileDTO.getBranch());
        existingProfile.setPhone(studentProfileDTO.getPhone());
        existingProfile.setPassout(studentProfileDTO.getPassout());

        StudentProfile updatedProfile = studentProfileRepository.save(existingProfile);

        return StudentProfileMapper.toResponseDTO(updatedProfile);
    }

    public void deleteStudentProfile(UUID userId) {
        StudentProfile existingProfile = studentProfileRepository.findById(userId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found"));

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getId());

        studentProfileRepository.deleteById(userId);
    }

}
