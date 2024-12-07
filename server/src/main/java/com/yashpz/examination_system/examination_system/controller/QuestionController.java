package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.service.FullQuestionService;
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
    private final FullQuestionService fullQuestionService;

    public QuestionController(QuestionService questionService, FullQuestionService fullQuestionService) {
        this.questionService = questionService;
        this.fullQuestionService = fullQuestionService;
    }

//    ------------------

    @PostMapping("/with-options")
    public ResponseEntity<ApiResponse<FullQuestionResponseDTO>> createQuestionWithOptions(@RequestBody @Valid FullQuestionRequestDTO questionDTO) throws IOException {
        System.out.println("QuestionWithOptionsDTO: " + questionDTO);
        FullQuestionResponseDTO fullQuestionResponseDTO = fullQuestionService.createQuestionWithOptions(questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, fullQuestionResponseDTO, "Question created successfully");
    }

    @PostMapping("/with-options/multiple")
    public ResponseEntity<ApiResponse<List<FullQuestionResponseDTO>>> createMultipleQuestionsWithOptions(@RequestBody List<FullQuestionRequestDTO> questionDTOList) {
        List<FullQuestionResponseDTO> questions = fullQuestionService.createQuestionWithOptions(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/full/{questionId}")
    public ResponseEntity<ApiResponse<FullQuestionResponseDTO>> getFullQuestion(@PathVariable UUID questionId) {
        FullQuestionResponseDTO fullQuestionResponseDTO = fullQuestionService.getFullQuestion(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, fullQuestionResponseDTO, "Question fetched successfully");
    }

    @GetMapping("/all/full")
    public ResponseEntity<ApiResponse<List<FullQuestionResponseDTO>>> getAllFullQuestion(@RequestParam(required = false) UUID categoryId,
                                                                                         @RequestParam(required = false) Difficulty difficulty,
                                                                                         @RequestParam(required = false) QuestionType type) {
        UUID category = type==QuestionType.MCQ ? categoryId : null;
        List<FullQuestionResponseDTO> fullQuestionResponseDTOS = fullQuestionService.getAllFullQuestions(category, difficulty, type);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, fullQuestionResponseDTOS, "Questions fetched successfully");
    }
//    ------------------

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(@RequestBody @Valid QuestionRequestDTO questionRequestDTO) throws IOException {
        QuestionResponseDTO question = questionService.createQuestion(questionRequestDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, question, "Question created successfully");
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> createMultipleQuestions(@RequestBody List<QuestionRequestDTO> questionRequestDTOList) {
        List<QuestionResponseDTO> questions = questionService.createQuestion(questionRequestDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, question, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getAllQuestions(@RequestParam(required = false) UUID categoryId,
                                             @RequestParam(required = false) Difficulty difficulty,
                                             @RequestParam(required = false) QuestionType type
    ) {
        List<QuestionResponseDTO> allQuestions = questionService.getAllQuestions(categoryId, difficulty, type);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, allQuestions, "Questions fetched successfully");
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(@PathVariable UUID questionId,@RequestBody @Valid QuestionRequestDTO questionRequestDTO) throws IOException {
        QuestionResponseDTO updatedQuestion = questionService.updateQuestion(questionId, questionRequestDTO, questionRequestDTO.getImageFile());
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