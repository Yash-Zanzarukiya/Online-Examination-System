package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import com.yashpz.examination_system.examination_system.service.CollegeService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("colleges")
public class CollegeController {

    private final CollegeService collegeService;

    public CollegeController(CollegeService collegeService) {
        this.collegeService = collegeService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CollegeDTO>> addCollege(@Valid @RequestBody CollegeDTO collegeDTO) {
        CollegeDTO newCollege = collegeService.createCollege(collegeDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, newCollege, "College added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CollegeDTO>> getCollegeById(@PathVariable UUID id) {
        CollegeDTO college = collegeService.getCollegeById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, college, "College fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CollegeDTO>>> getAllColleges() {
        List<CollegeDTO> colleges = collegeService.getAllColleges();
        return ApiResponseUtil.handleResponse(HttpStatus.OK, colleges, "Colleges fetched successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CollegeDTO>> updateCollege(@PathVariable UUID id, @Valid @RequestBody CollegeDTO collegeDTO) {
        CollegeDTO updatedCollege = collegeService.updateCollege(id, collegeDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedCollege, "College updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCollege(@PathVariable UUID id) {
        collegeService.deleteCollege(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "College deleted successfully");
    }
}
