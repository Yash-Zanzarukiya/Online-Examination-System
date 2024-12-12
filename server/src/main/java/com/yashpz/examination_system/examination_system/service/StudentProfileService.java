package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.StudentProfile.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.StudentProfileMapper;
import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.User;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.repository.StudentProfileRepository;
import com.yashpz.examination_system.examination_system.utils.ResourceAccessUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserService userService;
    private final CollegeService collegeService;
    private final ResourceAccessUtil resourceAccessUtil;

    public StudentProfileService(StudentProfileRepository studentProfileRepository, UserService userService, CollegeService collegeService, ResourceAccessUtil resourceAccessUtil) {
        this.studentProfileRepository = studentProfileRepository;
        this.userService = userService;
        this.collegeService = collegeService;
        this.resourceAccessUtil = resourceAccessUtil;
    }

    public StudentProfileResponseDTO createStudentProfile(StudentProfileRequestDTO studentProfileDTO) {
        StudentProfile existingProfile = studentProfileRepository.findByUserId(studentProfileDTO.getUserId());

        if (existingProfile!=null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Student Profile Already Exists");

        User user = userService.fetchUserById(studentProfileDTO.getUserId());
        College college = collegeService.fetchCollegeById(studentProfileDTO.getCollegeId());

        StudentProfile studentProfile = StudentProfileMapper.toEntity(studentProfileDTO);
        studentProfile.setUser(user);
        studentProfile.setCollege(college);

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
        StudentProfile studentProfile = fetchStudentProfileById(profileId);
        return StudentProfileMapper.toResponseDTO(studentProfile);
    }

    public StudentProfileResponseDTO updateStudentProfile(UUID profileId, StudentProfileRequestDTO studentProfileDTO) {
        StudentProfile existingProfile = fetchStudentProfileById(profileId);

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getAuth().getId());

        College college = collegeService.fetchCollegeById(studentProfileDTO.getCollegeId());

        StudentProfileMapper.updateEntity(existingProfile, studentProfileDTO);
        existingProfile.setCollege(college);

        studentProfileRepository.save(existingProfile);

        return StudentProfileMapper.toResponseDTO(existingProfile);
    }

    public void deleteStudentProfile(UUID userId) {
        StudentProfile existingProfile = fetchStudentProfileById(userId);

        resourceAccessUtil.AdminOrOwnerAccess(existingProfile.getUser().getAuth().getId());

        studentProfileRepository.deleteById(userId);
    }

    // <--------------- Helpers --------------->

    public StudentProfile fetchStudentProfileById(UUID id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Student Profile Not Found"));
    }
}
