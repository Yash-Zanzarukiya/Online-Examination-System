package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions.ActiveExamQuestionsDTO;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState.ActiveExamStateDTO;
import com.yashpz.examination_system.examination_system.service.ActiveExamService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("active-exam")
public class ActiveExamController {
    private final ActiveExamService activeExamService;

    public ActiveExamController(ActiveExamService activeExamService) {
        this.activeExamService = activeExamService;
    }

    @GetMapping("/questions/{scheduledExamId}")
    public ResponseEntity<ApiResponse<List<ActiveExamQuestionsDTO>>> getQuestionsForExam(@PathVariable UUID scheduledExamId) {
        List<ActiveExamQuestionsDTO> questionsForExam = activeExamService.getActiveExamQuestions(scheduledExamId);
       return ApiResponseUtil.handleResponse(HttpStatus.OK,questionsForExam, "Questions for exam fetched successfully");
    }

    @GetMapping("/state/{examAttemptId}")
    public ResponseEntity<ApiResponse<ActiveExamStateDTO>> getActiveExamState(@PathVariable UUID examAttemptId) {
        ActiveExamStateDTO activeExamState = activeExamService.getActiveExamState(examAttemptId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, activeExamState, "Questions for exam fetched successfully");
    }
}
