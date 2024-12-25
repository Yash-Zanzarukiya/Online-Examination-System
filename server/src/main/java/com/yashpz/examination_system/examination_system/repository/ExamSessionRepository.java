package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.model.ExamSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ExamSessionRepository extends JpaRepository<ExamSession, UUID> {
    ExamSession findByExamAttemptId(UUID examAttemptId);

    @Query("SELECT e FROM ExamSession e WHERE e.lastDisconnect < :threshold AND e.status = :status")
    List<ExamSession> findByLastDisconnectBeforeAndExamAttemptStatus(@Param("threshold") LocalDateTime threshold,
            @Param("status") ExamAttemptStatus status);

    @Query("SELECT e.sessionToken FROM ExamSession e WHERE e.examAttemptId = :examAttemptId")
    String getSessionTokenFromExamAttemptId(UUID examAttemptId);

    ExamSession findBySessionToken(String sessionToken);

    boolean existsByUserIdAndScheduledExamId(UUID userId, UUID scheduledExamId);
}
