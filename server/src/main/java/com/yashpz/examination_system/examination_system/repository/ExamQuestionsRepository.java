package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface ExamQuestionsRepository extends JpaRepository<ExamQuestions, UUID> {
    List<ExamQuestions> findByExamId(UUID examId);

    boolean existsByExamIdAndQuestionId(UUID examId, UUID questionId);

    @Query("SELECT eq FROM ExamQuestions eq JOIN FETCH eq.exam JOIN FETCH eq.question WHERE eq.exam.id = :examId")
    List<ExamQuestions> findByExamIdWithFetch(UUID examId);

    @Query("SELECT eq.question.id FROM ExamQuestions eq WHERE eq.exam.id = :examId AND eq.question.id IN :questionIds")
    List<UUID> findExistingQuestionIdsByExamIdAndQuestionIds(@Param("examId") UUID examId, @Param("questionIds") List<UUID> questionIds);

    @Modifying
    @Transactional
    @Query("DELETE FROM ExamQuestions eq WHERE eq.id IN :examQuestionIds")
    int deleteByQuestionIdIn(@Param("examQuestionIds") List<UUID> examQuestionIds);
}
