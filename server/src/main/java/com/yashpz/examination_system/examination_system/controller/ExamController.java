package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamResponseDTO;
import com.yashpz.examination_system.examination_system.service.ExamService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exam")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExamResponseDTO>> createExam(@Valid @RequestBody ExamRequestDTO dto) {
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, examService.createExam(dto), "Exam Created Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> getExamById(@PathVariable UUID id) {
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examService.getExamById(id), "Exam Retrieved Successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Iterable<ExamResponseDTO>>> getAllExams() {
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examService.getAllExams(), "Exams Retrieved Successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> updateExam(@PathVariable UUID id, @Valid @RequestBody ExamRequestDTO dto) {
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examService.updateExam(id, dto), "Exam Updated Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExam(@PathVariable UUID id) {
        examService.deleteExam(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam Deleted Successfully");
    }
}
