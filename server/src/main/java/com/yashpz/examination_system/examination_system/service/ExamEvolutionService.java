package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.McqSubmission;
import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import com.yashpz.examination_system.examination_system.repository.McqOptionRepository;
import com.yashpz.examination_system.examination_system.repository.McqSubmissionRepository;
import com.yashpz.examination_system.examination_system.repository.ProgrammingSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamEvolutionService {

    private final ExamAttemptRepository examAttemptRepository;
    private final McqSubmissionRepository mcqSubmissionRepository;
    private final ProgrammingSubmissionRepository programmingSubmissionRepository;
    private final McqOptionRepository mcqOptionRepository;

    // Triggered when the exam is submitted
    @Transactional
    public void calculateAndSaveMarks(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);

        List<McqSubmission> mcqSubmissions = mcqSubmissionRepository.findAllByExamAttemptId(examAttemptId);
        List<UUID> selectedOptionIds = mcqSubmissions.stream().map(McqSubmission::getSelectedOptionId).toList();

        List<UUID> correctOptions = mcqOptionRepository.findAllCorrectOptionsWithIdIn(selectedOptionIds);

        for (McqSubmission mcqSubmission : mcqSubmissions) {
            if (correctOptions.contains(mcqSubmission.getSelectedOptionId())) {
                mcqSubmission.setMarks(1);
            }else {
                mcqSubmission.setMarks(0);
            }
        }
        mcqSubmissionRepository.saveAll(mcqSubmissions);

        int correctCount = correctOptions.size();
        examAttempt.setScore(correctCount);
        examAttempt.setStatus(ExamAttemptStatus.TO_EVALUATE);
        examAttemptRepository.save(examAttempt);
    }

    @Transactional
    public void updateProgrammingMarks(UUID programmingSubmissionId, int marks) {
        ProgrammingSubmission programmingSubmission = fetchProgrammingSubmission(programmingSubmissionId);

        if (programmingSubmission.getQuestion().getMarks() < marks)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Marks cannot be greater than the total marks");

        programmingSubmission.setMarks(marks);
        programmingSubmissionRepository.save(programmingSubmission);

        UUID examAttemptId = programmingSubmission.getExamAttempt().getId();
        updateScore(examAttemptId);
    }

    @Transactional
    public void updateScore(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        int score = getExamAttemptScore(examAttemptId);
        examAttempt.setScore(score);
        examAttemptRepository.save(examAttempt);
    }

    //  <------------------ Helpers ------------------>

    public int getExamAttemptScore(UUID examAttemptId) {
        int mcqScore = getMcqSubmissionScore(examAttemptId);
        int programmingScore = getProgrammingSubmissionScore(examAttemptId);
        return mcqScore + programmingScore;
    }

    public int getMcqSubmissionScore(UUID examAttemptId) {
        return mcqSubmissionRepository.getMcqSubmissionScoreByExamAttemptId(examAttemptId);
    }

    public int getProgrammingSubmissionScore(UUID examAttemptId) {
        return programmingSubmissionRepository.getProgrammingSubmissionScoreByExamAttemptId(examAttemptId);
    }

    public ExamAttempt fetchExamAttemptById(UUID examAttemptId) {
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam attempt not found"));
    }

    public ProgrammingSubmission fetchProgrammingSubmission(UUID programmingSubmissionId) {
        return programmingSubmissionRepository.findById(programmingSubmissionId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Programming submission not found"));
    }

}
