package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.McqSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.ProgrammingSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.service.ExamResponseService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/exam-responses")
public class ExamResponsesController {

    private final ExamResponseService examResponseService;

    public ExamResponsesController(ExamResponseService examResponseService) {
        this.examResponseService = examResponseService;
    }

    @PostMapping("/mcq")
    public ResponseEntity<ApiResponse<Object>> saveMcqResponse(@RequestBody @Valid McqSubmissionRequestDTO mcqSubmissionRequestDTO) {
         examResponseService.saveMcqResponse(mcqSubmissionRequestDTO);
         return ApiResponseUtil.handleResponse(HttpStatus.OK,"Response saved successfully");
    }

    @PostMapping("/programming")
    public ResponseEntity<ApiResponse<Object>>  saveProgrammingResponse(@RequestBody @Valid ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO) {
         examResponseService.saveProgrammingResponse(programmingSubmissionRequestDTO);
            return ApiResponseUtil.handleResponse(HttpStatus.OK,"Response saved successfully");
    }

    @PatchMapping("/time-spent")
    public ResponseEntity<ApiResponse<Object>>  updateTimeSpentOnQuestion(
            @RequestParam("question") @Valid UUID questionId,
            @RequestParam("timeSpent") @Valid int timeSpent
    ) {
         examResponseService.updateTimeSpentOnQuestion(questionId, timeSpent);
         return ApiResponseUtil.handleResponse(HttpStatus.OK,"Time spent updated successfully");
    }
}
