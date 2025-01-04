package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.ExamQuestions.FullExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.service.ExamQuestionsService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exam-questions")
@RequiredArgsConstructor
public class ExamQuestionsController {

    private final ExamQuestionsService examQuestionsService;

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<List<ExamQuestionsResponseDTO>>> addQuestions(@RequestBody @Valid ExamQuestionsRequestDTO dto) {
        List<ExamQuestionsResponseDTO> createdExamQuestion = examQuestionsService.addQuestions(dto);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, createdExamQuestion, "Exam question added successfully");
    }

    @PostMapping("/remove")
    public ResponseEntity<ApiResponse<String>> removeQuestions(@RequestBody @Valid List<UUID> examQuestionIds) {
        examQuestionsService.removeQuestions(examQuestionIds);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam question deleted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamQuestionsResponseDTO>> getExamQuestionById(@PathVariable UUID id) {
        ExamQuestionsResponseDTO examQuestionsDTO = examQuestionsService.getExamQuestionById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTO, "Exam question fetched successfully");
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse<List<ExamQuestionsResponseDTO>>> getExamQuestionsByExamId(@PathVariable UUID examId) {
        List<ExamQuestionsResponseDTO> examQuestionsDTOs = examQuestionsService.getExamQuestionsByExamId(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Exam questions fetched successfully");
    }

    @GetMapping("/exam/{examId}/full")
    public ResponseEntity<ApiResponse<List<FullExamQuestionDTO>>> getFullExamQuestionsByExamId(@PathVariable UUID examId) {
        List<FullExamQuestionDTO> examQuestionsDTOs = examQuestionsService.getFullExamQuestionsByExamId(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Full exam questions fetched successfully");
    }
}
