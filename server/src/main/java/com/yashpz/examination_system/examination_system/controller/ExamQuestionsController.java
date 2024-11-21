package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Exam.AllExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.service.ExamQuestionsService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
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
    public ResponseEntity<ApiResponse<ExamQuestionsDTO>> createExamQuestion(@RequestBody ExamQuestionsDTO dto) {
        ExamQuestionsDTO createdExamQuestion = examQuestionsService.createExamQuestion(dto);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, createdExamQuestion, "Exam question added successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ExamQuestionsDTO>> getExamQuestionsById(@PathVariable UUID id) {
        ExamQuestionsDTO examQuestionsDTO = examQuestionsService.getExamQuestionsById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTO, "Exam question fetched successfully");
    }

    @GetMapping("/exam/{examId}")
    public ResponseEntity<ApiResponse<List<ExamQuestionsDTO>>> getExamQuestionsByExamId(@PathVariable UUID examId) {
        List<ExamQuestionsDTO> examQuestionsDTOs = examQuestionsService.getExamQuestionsByExamId(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Exam questions fetched successfully");
    }

    @GetMapping("/exam/{examId}/detailed")
    public ResponseEntity<ApiResponse<List<AllExamQuestionDTO>>> getAllExamQuestions(@PathVariable UUID examId) {
        List<AllExamQuestionDTO> examQuestionsDTOs = examQuestionsService.getAllExamQuestions(examId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examQuestionsDTOs, "Detailed exam questions fetched successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeQuestion(@PathVariable UUID id) {
        examQuestionsService.removeQuestion(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam question deleted successfully");
    }
}
