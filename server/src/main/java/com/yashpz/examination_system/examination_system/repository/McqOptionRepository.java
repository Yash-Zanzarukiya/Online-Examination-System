package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.McqOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface McqOptionRepository extends JpaRepository<McqOption, UUID> {
    List<McqOption> findAllByQuestionId(UUID questionId);

    List<McqOption> findAllByQuestionIdIn(List<UUID> questionIds);

    @Query("select mo.id from McqOption mo where mo.isCorrect = true and mo.id in ?1")
    List<UUID> findAllCorrectOptionsWithIdIn(List<UUID> optionIds);

    void deleteAllByQuestionId(UUID questionId);
}
