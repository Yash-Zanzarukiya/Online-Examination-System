package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ActiveExamActivities;
import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.dto.ExamActivity.ExamActivityResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamActivityMapper;
import com.yashpz.examination_system.examination_system.model.ExamActivity;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.repository.ExamActivityRepository;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamActivityService {
    private final ExamActivityRepository examActivityRepository;
    private final ExamAttemptRepository examAttemptRepository;

    public List<ExamActivityResponseDTO> fetchExamActivities(UUID examAttemptId) {
        List<ExamActivity> examActivities = examActivityRepository.findAllByExamAttemptId(examAttemptId);
        return ExamActivityMapper.toResponseDTO(examActivities, examAttemptId);
    }

    public void handleLogin(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.LOGIN, "Logged in to the exam");
        examActivityRepository.save(examActivity);
    }

    public void handleDisconnect(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);

        String message = "Disconnected from the exam";
        if (examAttempt.getStatus() == ExamAttemptStatus.SUBMITTED)
            message = "Disconnected from the exam after submission";

        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.DISCONNECT, message);

        examActivityRepository.save(examActivity);
    }

    public void handleStartExam(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.EXAM_START, "Started the exam");
        examActivityRepository.save(examActivity);
    }

    public void handleSubmitExam(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.EXAM_END, "Submitted the exam");
        examActivityRepository.save(examActivity);
    }

    public void handleExamEnd(UUID examAttemptId, String message) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.EXAM_END, message);
        examActivityRepository.save(examActivity);
    }

    public void handleTabSwitch(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.TAB_SWITCH, "Switched the tab");
        examActivityRepository.save(examActivity);
    }

    public void handleCopy(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.COPY, "Copy event detected");
        examActivityRepository.save(examActivity);
    }

    public void handlePaste(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.PASTE, "Paste event detected");
        examActivityRepository.save(examActivity);
    }

    public void handleQuestionNavigation(UUID examAttemptId, String message) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.QUESTION_NAVIGATION, message);
        examActivityRepository.save(examActivity);
    }

    public void handleAnswerSubmission(UUID examAttemptId, Integer questionNumber) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        String message = "Submitted answer for question " + questionNumber;
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.ANSWER_SUBMISSION, message);
        examActivityRepository.save(examActivity);
    }

    public void handleCloseAttempt(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        ExamActivity examActivity = ExamActivityMapper.toEntity(examAttempt, ActiveExamActivities.CLOSE_ATTEMPT, "Attempt to close browser or tab");
        examActivityRepository.save(examActivity);
    }

    // <--------------- Helpers --------------->

    public ExamAttempt fetchExamAttemptById(UUID examAttemptId) {
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam Attempt not found"));
    }

}
