package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Exam.AllExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.service.ExamQuestionsService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exam-questions")
public class ExamQuestionsController {

    private final ExamQuestionsService examQuestionsService;

    public ExamQuestionsController(ExamQuestionsService examQuestionsService) {
        this.examQuestionsService = examQuestionsService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<List<ExamQuestionsResponseDTO>>> addExamQuestion(@RequestBody @Valid ExamQuestionsRequestDTO dto) {
        List<ExamQuestionsResponseDTO> createdExamQuestion = examQuestionsService.createExamQuestions(dto);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, createdExamQuestion, "Exam question added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamQuestionsResponseDTO>> getExamQuestionsById(@PathVariable UUID id) {
        ExamQuestionsResponseDTO examQuestionsDTO = examQuestionsService.getExamQuestionsById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTO, "Exam question fetched successfully");
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse<List<ExamQuestionsResponseDTO>>> getExamQuestionsByExamId(@PathVariable UUID examId) {
        List<ExamQuestionsResponseDTO> examQuestionsDTOs = examQuestionsService.getExamQuestionsByExamId(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Exam questions fetched successfully");
    }

    @GetMapping("/exam/{examId}/detailed")
    public ResponseEntity<ApiResponse<List<AllExamQuestionDTO>>> getAllExamQuestions(@PathVariable UUID examId) {
        List<AllExamQuestionDTO> examQuestionsDTOs = examQuestionsService.getAllExamQuestions(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Detailed exam questions fetched successfully");
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<String>> removeQuestion(@RequestBody @Valid List<UUID> examQuestionIds) {
        examQuestionsService.removeQuestion(examQuestionIds);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam question deleted successfully");
    }
}
