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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ExamResponseDTO>> createExam(@Valid @RequestBody ExamRequestDTO dto) {
        ExamResponseDTO exam = examService.createExam(dto);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, exam, "Exam Created Successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> getExamById(@PathVariable UUID id) {
        ExamResponseDTO exam = examService.getExamById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, exam, "Exam Retrieved Successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ExamResponseDTO> >> getAllExams(
            @RequestParam(required = false, defaultValue = "false") boolean drafted
    ) {
        List<ExamResponseDTO> exams = examService.getAllExams(drafted);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, exams, "Exams Retrieved Successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamResponseDTO>> updateExam(@PathVariable UUID id, @Valid @RequestBody ExamRequestDTO dto) {
        ExamResponseDTO exam = examService.updateExam(id, dto);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, exam, "Exam Updated Successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteExam(@PathVariable UUID id) {
        examService.deleteExam(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam Deleted Successfully");
    }
}
