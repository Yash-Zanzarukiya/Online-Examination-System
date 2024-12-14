package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.service.ExamSessionService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exam-session")
public class ExamSessionController {
    private final ExamSessionService examSessionService;

    public ExamSessionController(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

    @GetMapping("/ping")
    public ResponseEntity<ApiResponse<String>> handleSessionPing(@RequestParam("remainingTime") Long remainingTime) {
        examSessionService.handleSessionPing(remainingTime);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Session ping handled successfully");
    }

    @GetMapping("/resume")
    public ResponseEntity<ApiResponse<String>> resumeSession() {
        examSessionService.handleResumingSession();
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Session resumed successfully");
    }
}
