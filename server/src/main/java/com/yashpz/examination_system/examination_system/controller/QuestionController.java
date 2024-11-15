package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.service.QuestionService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(@Valid QuestionDTO questionDTO) throws IOException {
        QuestionResponseDTO question = questionService.createQuestion(questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, question, "Question created successfully");
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> createMultipleQuestions(@RequestBody List<QuestionDTO> questionDTOList) {
        List<QuestionResponseDTO> questions = questionService.createMultipleQuestions(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, question, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getAllQuestions(@RequestParam(required = false) String categoryId,
                                             @RequestParam(required = false) String difficulty,
                                             @RequestParam(required = false) String type
    ) {
        List<QuestionResponseDTO> allQuestions = questionService.getAllQuestions(categoryId, difficulty, type);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, allQuestions, "Questions fetched successfully");
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(@PathVariable UUID questionId, @Valid QuestionDTO questionDTO) throws IOException {
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(questionId, questionDTO, questionDTO.getImageFile());
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedQuestion, "Question updated successfully");
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(@PathVariable String questionId) throws IOException {
        questionService.deleteQuestion(UUID.fromString(questionId));
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Question deleted successfully");
    }

    @DeleteMapping("/{questionId}/image")
    public ResponseEntity<ApiResponse<String>> deleteQuestionImage(@PathVariable UUID questionId) throws IOException {
        questionService.deleteQuestionImage(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Question image deleted successfully");
    }
}