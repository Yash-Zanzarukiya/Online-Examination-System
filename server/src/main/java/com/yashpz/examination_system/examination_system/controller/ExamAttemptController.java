package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.constants.ValidationGroups;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.service.ExamAttemptService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exam-attempt")
@RequiredArgsConstructor
public class ExamAttemptController {

    @Value("${SESSION_TOKEN_EXPIRY}")
    private int SESSION_TOKEN_EXPIRY;

    private final ExamAttemptService examAttemptService;

    @PostMapping("/make-attempt")
    public ResponseEntity<ApiResponse<String>> createExamAttempt(
            @RequestBody @Valid ExamAttemptRequestDTO examAttemptRequestDTO,
            HttpServletResponse response
    ) {
        String sessionToken = examAttemptService.createExamAttempt(examAttemptRequestDTO);

        ApiResponseUtil.setCookie(response, "session_token", sessionToken, SESSION_TOKEN_EXPIRY / 1000);

        return ApiResponseUtil.handleResponse(HttpStatus.CREATED,"Exam login successfully");
    }

    @PatchMapping("/status")
    public ResponseEntity<ApiResponse<String>> updateExamAttemptStatus(@RequestParam("examAttemptId") UUID examAttemptId, @RequestParam("status") ExamAttemptStatus status) {
        examAttemptService.updateExamAttemptStatus(examAttemptId, status);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam attempt status updated successfully");
    }

    @PatchMapping("/programming-marks")
    public ResponseEntity<ApiResponse<String>> updateProgrammingMarks(@RequestParam("programmingSubmissionId") UUID programmingSubmissionId, @RequestParam("marks") int marks) {
        examAttemptService.updateProgrammingMarks(programmingSubmissionId, marks);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Programming marks updated successfully");
    }
}
