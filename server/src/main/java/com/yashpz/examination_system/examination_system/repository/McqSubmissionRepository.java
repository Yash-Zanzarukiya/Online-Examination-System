package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.McqSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface McqSubmissionRepository extends JpaRepository<McqSubmission, UUID> {
    McqSubmission findByExamAttemptIdAndQuestionId(UUID examAttemptId, UUID questionId);

    @Modifying
    @Query("update McqSubmission ms set ms.timeSpent = ms.timeSpent + ?3 where ms.examAttempt.id = ?1 and ms.question.id = ?2")
    void updateTimeSpentOnQuestion(UUID examAttemptId, UUID questionId, int timeSpent);

    List<McqSubmission> findAllByExamAttemptId(UUID examAttemptId);

    @Query("select sum(ms.marks) from McqSubmission ms where ms.examAttempt.id = ?1")
    int getMcqSubmissionScoreByExamAttemptId(UUID examAttemptId);

    Boolean existsByExamAttemptIdAndQuestionId(UUID examAttemptId, UUID questionId);
}
