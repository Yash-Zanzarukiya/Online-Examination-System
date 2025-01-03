package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.service.QuestionFileService;
import com.yashpz.examination_system.examination_system.service.QuestionService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionFileService questionFileService;

    public QuestionController(QuestionService questionService, QuestionFileService questionFileService) {
        this.questionService = questionService;
        this.questionFileService = questionFileService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(@RequestBody @Valid QuestionRequestDTO questionRequestDTO) {
        QuestionResponseDTO question = questionService.createQuestion(questionRequestDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, question, "Question created successfully");
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> createBulkQuestions(@RequestBody List<QuestionRequestDTO> questionRequestDTOList) {
        List<QuestionResponseDTO> questions = questionService.createBulkQuestions(questionRequestDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadQuestions(@RequestBody MultipartFile file) {
        questionFileService.processQuestionsFile(file);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, "Questions saved successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, question, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getAllQuestions(@RequestParam(required = false) UUID categoryId,
                                             @RequestParam(required = false) Difficulty difficulty,
                                             @RequestParam(required = false) QuestionType type,
                                                @RequestParam(required = false) Integer marks
    ) {
        List<QuestionResponseDTO> allQuestions = questionService.getAllQuestions(categoryId, difficulty, type, marks);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, allQuestions, "Questions fetched successfully");
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(@PathVariable UUID questionId,@RequestBody @Valid QuestionRequestDTO questionRequestDTO) {
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(questionId, questionRequestDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedQuestion, "Question updated successfully");
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<String>> deleteQuestion(@PathVariable String questionId) {
        questionService.deleteQuestion(UUID.fromString(questionId));
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Question deleted successfully");
    }

    @DeleteMapping("/{questionId}/image")
    public ResponseEntity<ApiResponse<String>> deleteQuestionImage(@PathVariable UUID questionId) {
        questionService.deleteQuestionImage(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Question image deleted successfully");
    }
}