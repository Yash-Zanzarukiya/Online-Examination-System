package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Exam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
    @Query("SELECT e FROM Exam e WHERE e.id NOT IN (SELECT DISTINCT se.exam.id FROM ScheduleExam se)")
    List<Exam> findAllDraftedExams();
}

