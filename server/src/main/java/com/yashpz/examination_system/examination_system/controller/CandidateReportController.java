package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.*;
import com.yashpz.examination_system.examination_system.service.CandidateReportService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate-report")
@RequiredArgsConstructor
public class CandidateReportController {
    private final CandidateReportService candidateReportService;

    @GetMapping("/exam-candidate-states")
    public ResponseEntity<ApiResponse<List<CandidateStateDTO>>> getAllExamCandidateStates(@RequestParam("scheduledExamId") UUID scheduledExamId) {
        List<CandidateStateDTO> candidateStates = candidateReportService.getAllCandidateStates(scheduledExamId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, candidateStates, "Candidate states fetched successfully");
    }

    @GetMapping("/candidate-state")
    public ResponseEntity<ApiResponse<CandidateStateDTO>> getCandidateState(@RequestParam("examAttemptId") UUID examAttemptId) {
        CandidateStateDTO candidateState = candidateReportService.getCandidateState(examAttemptId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, candidateState, "Candidate state fetched successfully");
    }

    @GetMapping("/score-distribution")
    public ResponseEntity<ApiResponse<ScoreDistributionDTO>> getCandidateScoreDistribution(@RequestParam("examAttemptId") UUID examAttemptId) {
        ScoreDistributionDTO scoreDistribution = candidateReportService.getCandidateScoreDistribution(examAttemptId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, scoreDistribution, "Candidate score distribution fetched successfully");
    }

    @GetMapping("/questions-analysis")
    public ResponseEntity<ApiResponse<List<QuestionsAnalysisDTO>>> getCandidateQuestionsAnalysis(@RequestParam("examAttemptId") UUID examAttemptId) {
        List<QuestionsAnalysisDTO> questionsAnalysis = candidateReportService.getCandidateQuestionsAnalysis(examAttemptId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, questionsAnalysis, "Candidate questions analysis fetched successfully");
    }

    @GetMapping("/mcq-submission")
    public ResponseEntity<ApiResponse<DetailedMcqSubmissionDTO>> getFullMcqSubmission(@RequestParam("examAttemptId") UUID examAttemptId, @RequestParam("questionId") UUID questionId) {
        DetailedMcqSubmissionDTO mcqSubmission = candidateReportService.getFullMcqSubmission(examAttemptId, questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, mcqSubmission, "MCQ submission fetched successfully");
    }

    @GetMapping("/programming-submission")
    public ResponseEntity<ApiResponse<DetailedProgrammingSubmissionDTO>> getFullProgrammingSubmission(@RequestParam("examAttemptId") UUID examAttemptId, @RequestParam("questionId") UUID questionId) {
        DetailedProgrammingSubmissionDTO programmingSubmission = candidateReportService.getFullProgrammingSubmission(examAttemptId, questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, programmingSubmission, "Programming submission fetched successfully");
    }

}
