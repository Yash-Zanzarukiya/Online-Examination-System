package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.User.StudentProfileRequestDTO;
import com.yashpz.examination_system.examination_system.dto.User.StudentProfileResponseDTO;
import com.yashpz.examination_system.examination_system.service.StudentProfileService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("student-profile")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentProfileResponseDTO>> createStudentProfile(@Valid @RequestBody StudentProfileRequestDTO studentProfileDTO) {
        StudentProfileResponseDTO createdProfile = studentProfileService.createStudentProfile(studentProfileDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, createdProfile, "Student Profile Created Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentProfileResponseDTO>> getStudentProfileById(@PathVariable UUID id) {
        StudentProfileResponseDTO studentProfileDTO = studentProfileService.getStudentProfileById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, studentProfileDTO, "Student Profile Fetched Successfully");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse<StudentProfileResponseDTO>> getStudentProfileByUserId(@PathVariable UUID id) {
        StudentProfileResponseDTO studentProfileDTO = studentProfileService.getStudentProfileByUserId(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, studentProfileDTO, "Student Profile Fetched Successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentProfileResponseDTO>> updateStudentProfile(@PathVariable UUID id, @Valid @RequestBody StudentProfileRequestDTO studentProfileDTO) {
        StudentProfileResponseDTO updatedProfile = studentProfileService.updateStudentProfile(id, studentProfileDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedProfile, "Student Profile Updated Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudentProfile(@PathVariable UUID id) {
        studentProfileService.deleteStudentProfile(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Student Profile Deleted Successfully");
    }
}
