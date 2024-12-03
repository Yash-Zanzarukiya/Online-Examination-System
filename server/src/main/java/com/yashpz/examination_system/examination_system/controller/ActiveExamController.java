package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestionsDTO;
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

    @GetMapping("/{examId}/questions")
    public ResponseEntity<ApiResponse<List<ActiveExamQuestionsDTO>>> getQuestionsForExam(@PathVariable UUID examId) {
        List<ActiveExamQuestionsDTO> questionsForExam = activeExamService.getQuestionsForExam(examId);
       return ApiResponseUtil.handleResponse(HttpStatus.OK,questionsForExam, "Questions for exam fetched successfully");
    }
}
