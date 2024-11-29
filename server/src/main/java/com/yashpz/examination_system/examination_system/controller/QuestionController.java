package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionWithOptionsDTO;
import com.yashpz.examination_system.examination_system.service.MultipleQuestionService;
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
    private final MultipleQuestionService multipleQuestionService;

    public QuestionController(QuestionService questionService, MultipleQuestionService multipleQuestionService) {
        this.questionService = questionService;
        this.multipleQuestionService = multipleQuestionService;
    }

//    ------------------

    @PostMapping("/with-options")
    public ResponseEntity<ApiResponse<FullQuestionDTO>> createQuestionWithOptions(@RequestBody @Valid QuestionWithOptionsDTO questionDTO) throws IOException {
        System.out.println("QuestionWithOptionsDTO: " + questionDTO);
        FullQuestionDTO fullQuestionDTO = multipleQuestionService.createQuestionWithOptions(questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, fullQuestionDTO, "Question created successfully");
    }

    @PostMapping("/with-options/multiple")
    public ResponseEntity<ApiResponse<List<FullQuestionDTO>>> createMultipleQuestionsWithOptions(@RequestBody List<QuestionWithOptionsDTO> questionDTOList) {
        List<FullQuestionDTO> questions = multipleQuestionService.createQuestionWithOptions(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

//    ------------------

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> createQuestion(@RequestBody @Valid QuestionDTO questionDTO) throws IOException {
        QuestionResponseDTO question = questionService.createQuestion(questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, question, "Question created successfully");
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> createMultipleQuestions(@RequestBody List<QuestionDTO> questionDTOList) {
        List<QuestionResponseDTO> questions = questionService.createQuestion(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> getQuestionById(@PathVariable UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, question, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponseDTO>>> getAllQuestions(@RequestParam(required = false) UUID categoryId,
                                             @RequestParam(required = false) String difficulty,
                                             @RequestParam(required = false) String type
    ) {
        List<QuestionResponseDTO> allQuestions = questionService.getAllQuestions(categoryId, difficulty, type);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, allQuestions, "Questions fetched successfully");
    }

    @GetMapping("/full/{questionId}")
    public ResponseEntity<ApiResponse<FullQuestionDTO>> getQuestionWithOptions(@PathVariable UUID questionId) {
        FullQuestionDTO fullQuestionDTO = multipleQuestionService.getQuestionWithOptions(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, fullQuestionDTO, "Question fetched successfully");
    }

    @GetMapping("/all/full")
    public ResponseEntity<ApiResponse<List<FullQuestionDTO>>> getAllQuestionWithOptions(@RequestParam(required = false) UUID categoryId,
                                                                                        @RequestParam(required = false) String difficulty,
                                                                                        @RequestParam(required = false) String type) {
        List<FullQuestionDTO> fullQuestionDTOs = multipleQuestionService.getAllQuestionWithOptions(categoryId, difficulty, type);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, fullQuestionDTOs, "Questions fetched successfully");
    }

    @PatchMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponseDTO>> updateQuestion(@PathVariable UUID questionId,@RequestBody @Valid QuestionDTO questionDTO) throws IOException {
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