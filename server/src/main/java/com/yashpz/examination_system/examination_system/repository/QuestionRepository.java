package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
}
