package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, UUID> {
//    List<ExamAttempt> findByExamId(UUID examId);
//    List<ExamAttempt> findByUserId(UUID userId);
//    ExamAttempt findByUserIdAndExamId(UUID userId, UUID examId);
//    ExamAttempt findByUserIdAndExamIdAndAttemptStatus(UUID userId, UUID examId, ExamAttemptStatus attemptStatus);
//    List<ExamAttempt> findByExamIdAndAttemptStatus(UUID examId, ExamAttemptStatus attemptStatus);
//    List<ExamAttempt> findByAttemptStatus(ExamAttemptStatus attemptStatus);
//    List<ExamAttempt> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);
}
