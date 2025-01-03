package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduleExamRepository extends JpaRepository<ScheduleExam, UUID> {
    @Query("SELECT se FROM ScheduleExam se " +
            "WHERE (:examId IS NULL OR se.exam.id = :examId) " +
            "AND (:collegeId IS NULL OR se.college.id = :collegeId) " +
            "AND (:status IS NULL OR se.status = :status)")
    List<ScheduleExam> findSchedulesByFilters(@Param("examId") UUID examId, @Param("collegeId") UUID collegeId, @Param("status") ScheduledExamStatus status);

    @Query("SELECT se.exam.id FROM ScheduleExam se WHERE se.id = :scheduledExamId")
    UUID getExamIdByScheduledExamId(UUID scheduledExamId);

    @Modifying
    @Query("UPDATE ScheduleExam se SET se.status = :status WHERE se.id = :scheduledExamId")
    Integer updateScheduleExamStatus(@Param("scheduledExamId") UUID scheduledExamId, @Param("status") ScheduledExamStatus status);
}
