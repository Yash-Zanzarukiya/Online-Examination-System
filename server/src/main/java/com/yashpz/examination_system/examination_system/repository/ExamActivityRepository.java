package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.ExamActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExamActivityRepository extends JpaRepository<ExamActivity, UUID> {
    List<ExamActivity> findAllByExamAttemptId(UUID examAttemptId);
}
