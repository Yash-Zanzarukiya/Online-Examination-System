package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.model.ProgrammingQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProgrammingQuestionRepository extends JpaRepository<ProgrammingQuestion, UUID> {
    public ProgrammingQuestion findByQuestionId(UUID questionId);
    public List<ProgrammingQuestion> findByQuestionIdIn(List<UUID> questionIds);

    @Query("SELECT pq FROM ProgrammingQuestion pq JOIN FETCH pq.question q WHERE q.difficulty = :difficulty")
    List<ProgrammingQuestion> findAllByQuestionDifficulty(@Param("difficulty") Difficulty difficulty);

    @Query("SELECT pq FROM ProgrammingQuestion pq JOIN FETCH pq.question q WHERE q.difficulty = :difficulty AND q.questionType = :questionType")
    List<ProgrammingQuestion> findAllByQuestionDifficultyAndQuestionType(@Param("difficulty") Difficulty difficulty, @Param("questionType") QuestionType questionType);
}
