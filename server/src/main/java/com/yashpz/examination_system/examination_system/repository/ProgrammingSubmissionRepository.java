package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProgrammingSubmissionRepository extends JpaRepository<ProgrammingSubmission, UUID> {
    ProgrammingSubmission findByExamAttemptIdAndQuestionId(UUID examAttemptId, UUID questionId);

    List<ProgrammingSubmission> findAllByExamAttemptId(UUID examAttemptId);

    @Modifying
    @Query("update ProgrammingSubmission pq set pq.timeSpent = pq.timeSpent + ?3 where pq.examAttempt = ?1 and pq.question = ?2")
    void updateTimeSpentOnQuestion(UUID examAttemptId, UUID questionId, int timeSpent);

    @Query("select sum(pq.marks) from ProgrammingSubmission pq where pq.examAttempt.id = ?1")
    int getProgrammingSubmissionScoreByExamAttemptId(UUID examAttemptId);
}
