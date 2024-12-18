package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface ProgrammingSubmissionRepository extends JpaRepository<ProgrammingSubmission, UUID> {
    ProgrammingSubmission findByExamAttemptIdAndQuestionId(UUID examAttemptId, UUID questionId);

    @Modifying
    @Query("update ProgrammingSubmission pq set pq.timeSpent = pq.timeSpent + ?3 where pq.examAttempt = ?1 and pq.question = ?2")
    void updateTimeSpentOnQuestion(UUID examAttemptId, UUID questionId, int timeSpent);
}
