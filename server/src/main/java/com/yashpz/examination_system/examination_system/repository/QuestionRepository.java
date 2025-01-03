package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    @Query("SELECT q FROM Question q WHERE (:categoryId IS NULL OR q.category.id = :categoryId) AND (:difficulty IS NULL OR q.difficulty = :difficulty) AND (:type IS NULL OR q.type = :type)  AND (:marks IS NULL OR q.marks = :marks)")
    List<Question> findAllByFilters(@Param("categoryId") UUID categoryId, @Param("difficulty") Difficulty difficulty, @Param("type") QuestionType type, @Param("marks") Integer marks);
}
