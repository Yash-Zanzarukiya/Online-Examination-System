package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.McqOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface McqOptionRepository extends JpaRepository<McqOption, UUID> {
    List<McqOption> findAllByQuestionId(UUID questionId);
    void deleteAllByQuestionId(UUID questionId);
}
