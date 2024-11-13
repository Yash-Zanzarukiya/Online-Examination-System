package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExamRepository extends JpaRepository<Exam, UUID> {
}
