package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.service.McqQuestionService;
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
@RequestMapping("/mcq-questions")
@RequiredArgsConstructor
public class McqQuestionController {

    private final McqQuestionService mcqQuestionService;

    @PostMapping
    public ResponseEntity<ApiResponse<McqQuestionResponseDTO>> createMcqQuestion(@RequestBody @Valid McqQuestionRequestDTO questionDTO) {
        System.out.println("QuestionWithOptionsDTO: " + questionDTO);
        McqQuestionResponseDTO mcqQuestionResponseDTO = mcqQuestionService.createMcqQuestion(questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, mcqQuestionResponseDTO, "Question created successfully");
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<McqQuestionResponseDTO>>> createBulkMcqQuestions(@RequestBody List<McqQuestionRequestDTO> questionDTOList) {
        List<McqQuestionResponseDTO> questions = mcqQuestionService.createBulkMcqQuestions(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<ApiResponse<McqQuestionResponseDTO>> getMcqQuestion(@PathVariable UUID questionId) {
        McqQuestionResponseDTO mcqQuestionResponseDTO = mcqQuestionService.getMcqQuestion(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, mcqQuestionResponseDTO, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<McqQuestionResponseDTO>>> getAllMcqQuestions(@RequestParam(required = false) UUID categoryId,
                                                                                        @RequestParam(required = false) Difficulty difficulty) {
        List<McqQuestionResponseDTO> mcqQuestionResponseDTOS = mcqQuestionService.getAllMcqQuestions(categoryId, difficulty);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, mcqQuestionResponseDTOS, "Questions fetched successfully");
    }
}
