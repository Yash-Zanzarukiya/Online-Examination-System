package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.service.ProgrammingQuestionService;
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
@RequestMapping("/programming-questions")
@RequiredArgsConstructor
public class ProgrammingQuestionController {

    private final ProgrammingQuestionService programmingQuestionService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProgrammingQuestionResponseDTO>> createProgrammingQuestion(@RequestBody @Valid ProgrammingQuestionRequestDTO questionDTO) {
         ProgrammingQuestionResponseDTO programmingQuestionResponseDTO = programmingQuestionService.createProgrammingQuestion(questionDTO);
         return ApiResponseUtil.handleResponse(HttpStatus.CREATED, programmingQuestionResponseDTO, "Question created successfully");
    }

    @PostMapping("/bulk")
    public ResponseEntity<ApiResponse<List<ProgrammingQuestionResponseDTO>>> createBulkProgrammingQuestions(@RequestBody List<ProgrammingQuestionRequestDTO> questionDTOList) {
        List<ProgrammingQuestionResponseDTO> questions = programmingQuestionService.createBulkProgrammingQuestions(questionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, questions, "Questions created successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgrammingQuestionResponseDTO>> getProgrammingQuestion(@PathVariable UUID id) {
        ProgrammingQuestionResponseDTO programmingQuestionResponseDTO = programmingQuestionService.getProgrammingQuestionById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, programmingQuestionResponseDTO, "Question fetched successfully");
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<ProgrammingQuestionResponseDTO>> getProgrammingQuestionByQuestionId(@PathVariable UUID questionId) {
        ProgrammingQuestionResponseDTO programmingQuestionResponseDTO = programmingQuestionService.getProgrammingQuestionByQuestionId(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, programmingQuestionResponseDTO, "Question fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProgrammingQuestionResponseDTO>>> getAllProgrammingQuestions(@RequestParam(required = false) Difficulty difficulty) {
        List<ProgrammingQuestionResponseDTO> programmingQuestionResponseDTOS = programmingQuestionService.getAllProgrammingQuestions(difficulty);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, programmingQuestionResponseDTOS, "Questions fetched successfully");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgrammingQuestionResponseDTO>> updateProgrammingQuestion(@PathVariable UUID id, @RequestBody ProgrammingQuestionRequestDTO questionDTO) {
        ProgrammingQuestionResponseDTO programmingQuestionResponseDTO = programmingQuestionService.updateProgrammingQuestion(id, questionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, programmingQuestionResponseDTO, "Question updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProgrammingQuestion(@PathVariable UUID id) {
        programmingQuestionService.deleteProgrammingQuestion(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Question deleted successfully");
    }
}
