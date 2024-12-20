package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedMarksDTO;
import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedScoresDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AggregatedService{

    @PersistenceContext
    private EntityManager entityManager;

    public AggregatedMarksDTO getAggregatedMarks(UUID examId) {
        String query = "SELECT new com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedMarksDTO(" +
                "SUM(q.marks), " +
                "SUM(CASE WHEN q.type = 'MCQ' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.type = 'PROGRAMMING' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.difficulty = 'EASY' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.difficulty = 'MEDIUM' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.difficulty = 'HARD' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.type = 'MCQ' AND cat.name = 'aptitude' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.type = 'MCQ' AND cat.name = 'technical' THEN q.marks ELSE 0 END), " +
                "SUM(CASE WHEN q.type = 'MCQ' AND cat.name = 'programming' THEN q.marks ELSE 0 END) ) " +
                "FROM ExamQuestions eq " +
                "JOIN eq.question q " +
                "LEFT JOIN q.category cat " +
                "WHERE eq.exam.id = :examId " +
                "GROUP BY eq.exam.id";

        return entityManager.createQuery(query, AggregatedMarksDTO.class)
                .setParameter("examId", examId)
                .getSingleResult();
    }

    public AggregatedScoresDTO getAggregatedExamAttemptScores(UUID examAttemptId) {
        String query = "SELECT new com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedScoresDTO(" +
                "(COALESCE(SUM(ms.marks), 0) + COALESCE(SUM(ps.marks), 0)), " +
                "COALESCE(SUM(ms.marks), 0), " +
                "COALESCE(SUM(ps.marks), 0), " +
                "COALESCE(SUM(CASE WHEN q.difficulty = 'EASY' THEN COALESCE(ms.marks, 0) + COALESCE(ps.marks, 0) END), 0), " +
                "COALESCE(SUM(CASE WHEN q.difficulty = 'MEDIUM' THEN COALESCE(ms.marks, 0) + COALESCE(ps.marks, 0) END), 0), " +
                "COALESCE(SUM(CASE WHEN q.difficulty = 'HARD' THEN COALESCE(ms.marks, 0) + COALESCE(ps.marks, 0) END), 0), " +
                "COALESCE(SUM(CASE WHEN ms.id IS NOT NULL AND c.name = 'aptitude' THEN ms.marks END), 0), " +
                "COALESCE(SUM(CASE WHEN ms.id IS NOT NULL AND c.name = 'technical' THEN ms.marks END), 0), " +
                "COALESCE(SUM(CASE WHEN ms.id IS NOT NULL AND c.name = 'programming' THEN ms.marks END), 0)) " +
                "FROM McqSubmission ms " +
                "FULL JOIN ProgrammingSubmission ps ON ms.examAttempt.id = ps.examAttempt.id AND ms.question.id = ps.question.id " +
                "JOIN Question q ON COALESCE(ms.question.id, ps.question.id) = q.id " +
                "LEFT JOIN q.category c " +
                "WHERE COALESCE(ms.examAttempt.id, ps.examAttempt.id) = :examAttemptId " +
                "GROUP BY COALESCE(ms.examAttempt.id, ps.examAttempt.id)";

        return entityManager.createQuery(query, AggregatedScoresDTO.class)
                .setParameter("examAttemptId", examAttemptId)
                .getSingleResult();
    }

}
