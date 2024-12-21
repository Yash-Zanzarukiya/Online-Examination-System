package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedMarksDTO;
import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedScoresDTO;
import com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.*;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.CandidateReportMapper;
import com.yashpz.examination_system.examination_system.model.*;
import com.yashpz.examination_system.examination_system.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CandidateReportService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ExamQuestionsRepository examQuestionsRepository;
    private final McqSubmissionRepository mcqSubmissionRepository;
    private final ProgrammingSubmissionRepository programmingSubmissionRepository;
    private final ScheduleExamService scheduleExamService;
    private final McqQuestionService mcqQuestionService;
    private final ProgrammingQuestionService programmingQuestionService;
    private final AggregatedService aggregatedService;

    public List<CandidateStateDTO> getAllCandidateStates(UUID scheduledExamId) {
        UUID examId = scheduleExamService.fetchScheduleExamById(scheduledExamId).getExam().getId();
        int totalExamMarks = examQuestionsRepository.getTotalExamMarks(examId);
        return examAttemptRepository.getAllCandidateStates(scheduledExamId, totalExamMarks);
    }

    public CandidateStateDTO getCandidateState(UUID examAttemptId) {
        UUID examId = fetchExamAttemptById(examAttemptId).getExam().getExam().getId();
        int totalExamMarks = examQuestionsRepository.getTotalExamMarks(examId);
        return examAttemptRepository.getCandidateState(examAttemptId, totalExamMarks);
    }

    public ScoreDistributionDTO getCandidateScoreDistribution(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        AggregatedMarksDTO aggregatedMarks = aggregatedService.getAggregatedMarks(examAttempt.getExam().getExam().getId());
        AggregatedScoresDTO aggregatedScores = aggregatedService.getAggregatedExamAttemptScores(examAttemptId);
        return new ScoreDistributionDTO(aggregatedMarks, aggregatedScores);
    }

    public List<QuestionsAnalysisDTO> getCandidateQuestionsAnalysis(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        UUID examId = examAttempt.getExam().getExam().getId();

        List<ExamQuestions> examQuestionsList = examQuestionsRepository.findByExamIdWithFetch(examId);

        Map<UUID, Submission> mcqSubmissionsMap = mcqSubmissionRepository
                .findAllByExamAttemptId(examAttemptId)
                .stream()
                .collect(Collectors.toMap(
                        ms -> ms.getQuestion().getId(),
                        McqSubmissionAdapter::new
                ));

        Map<UUID, Submission> programmingSubmissionsMap = programmingSubmissionRepository
                .findAllByExamAttemptId(examAttemptId)
                .stream()
                .collect(Collectors.toMap(
                        ps -> ps.getQuestion().getId(),
                        ProgrammingSubmissionAdapter::new
                ));

        QuestionsAnalysisDTO mcqAnalysis = analyzeQuestionPerformance(
                examQuestionsList, mcqSubmissionsMap, QuestionType.MCQ, "MCQ");

        QuestionsAnalysisDTO programmingAnalysis = analyzeQuestionPerformance(
                examQuestionsList, programmingSubmissionsMap, QuestionType.PROGRAMMING, "Programming");

        return List.of(mcqAnalysis, programmingAnalysis);
    }

    public DetailedMcqSubmissionDTO getFullMcqSubmission(UUID examAttemptId, UUID questionId) {
        McqSubmission mcqSubmission = fetchMcqSubmissionByExamAttemptIdAndQuestionId(examAttemptId, questionId);
        McqQuestionResponseDTO question = mcqQuestionService.getMcqQuestion(questionId);
        return CandidateReportMapper.toDetailedMcqSubmissionDTO(question, mcqSubmission);
    }

    public DetailedProgrammingSubmissionDTO getFullProgrammingSubmission(UUID examAttemptId, UUID questionId) {
        ProgrammingSubmission programmingSubmission = programmingSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttemptId, questionId);
        ProgrammingQuestionResponseDTO question = programmingQuestionService.getProgrammingQuestionByQuestionId(questionId);
        return CandidateReportMapper.toDetailedProgrammingSubmissionDTO(question, programmingSubmission);
    }

    // <--------------- Helpers --------------->

    private QuestionsAnalysisDTO analyzeQuestionPerformance(
            List<ExamQuestions> examQuestionsList,
            Map<UUID, ? extends Submission> submissionsMap,
            QuestionType questionType,
            String sectionName) {

        List<QuestionPerformanceDTO> performances = new ArrayList<>();
        int totalQuestions = 0, attemptedQuestions = 0, totalMarks = 0, score = 0, timeTaken = 0;

        for (ExamQuestions eq : examQuestionsList) {
            if (eq.getQuestion().getType() != questionType) continue;

            totalQuestions++;
            totalMarks += eq.getQuestion().getMarks();

            Submission submission = submissionsMap.get(eq.getQuestion().getId());
            if (submission != null) {
                attemptedQuestions++;
                score += submission.getMarks();
                timeTaken += submission.getTimeSpent();
                performances.add(CandidateReportMapper.toQuestionPerformanceDTO(eq, submission));
            } else {
                performances.add(CandidateReportMapper.toQuestionPerformanceDTO(eq, null));
            }
        }
        return new QuestionsAnalysisDTO(sectionName, totalQuestions, attemptedQuestions, score, totalMarks, timeTaken, performances);
    }

    public ExamAttempt fetchExamAttemptById(UUID examAttemptId) {
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam Attempt not found"));
    }

    public McqSubmission fetchMcqSubmissionByExamAttemptIdAndQuestionId(UUID examAttemptId, UUID questionId) {
        McqSubmission mcqSubmission = mcqSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttemptId, questionId);
        if (mcqSubmission == null) throw new ApiError(HttpStatus.NOT_FOUND, "MCQ Submission not found");
        return mcqSubmission;
    }
}
