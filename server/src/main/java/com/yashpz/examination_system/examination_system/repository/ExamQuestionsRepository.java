package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ExamQuestionsRepository extends JpaRepository<ExamQuestions, UUID> {
    List<ExamQuestions> findByExamId(UUID examId);

    boolean existsByExamIdAndQuestionId(UUID examId, UUID questionId);

    @Query("SELECT eq FROM ExamQuestions eq JOIN FETCH eq.exam JOIN FETCH eq.question WHERE eq.exam.id = :examId")
    List<ExamQuestions> findByExamIdWithFetch(UUID examId);
}