package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.CandidateStateDTO;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, UUID> {
   ExamAttempt findByUserIdAndExamId(UUID userId, UUID examId);

   @Query("SELECT new com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.CandidateStateDTO(ea.id, ea.user.id, ea.user.auth.email, ea.user.fullName, ea.status, ea.score, :totalExamMarks, ea.startTime, ea.endTime) FROM ExamAttempt ea WHERE ea.exam.id = :scheduledExamId")
   List<CandidateStateDTO> getAllCandidateStates(UUID scheduledExamId, int totalExamMarks);

   @Query("SELECT new com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.CandidateStateDTO(ea.id, ea.user.id, ea.user.auth.email, ea.user.fullName, ea.status, ea.score, :totalExamMarks, ea.startTime, ea.endTime) FROM ExamAttempt ea WHERE ea.id = :examAttemptId")
   CandidateStateDTO getCandidateState(UUID examAttemptId, int totalExamMarks);

   @Query("SELECT ea.exam.exam.id FROM ExamAttempt ea WHERE ea.id = :examAttemptId")
   UUID getExamIdFromExamAttemptId(UUID examAttemptId);

    @Query("SELECT ea.exam.id FROM ExamAttempt ea WHERE ea.id = :examAttemptId")
    UUID getScheduledExamIdFromExamAttemptId(UUID examAttemptId);

    @Query("SELECT ea.exam.exam.timeLimit FROM ExamAttempt ea WHERE ea.id = :examAttemptId")
    Integer getExamTimeFromExamAttemptId(UUID examAttemptId);

    @Query("SELECT ea.status FROM ExamAttempt ea WHERE ea.id = :examAttemptId")
    ExamAttemptStatus getStatusById(UUID examAttemptId);

    @Modifying
    @Query("UPDATE ExamAttempt ea SET ea.status = :status WHERE ea.id = :examAttemptId")
    void updateStatus(UUID examAttemptId, ExamAttemptStatus status);
}
