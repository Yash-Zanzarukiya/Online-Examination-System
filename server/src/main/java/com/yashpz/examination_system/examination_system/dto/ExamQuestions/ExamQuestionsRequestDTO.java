package com.yashpz.examination_system.examination_system.dto.ExamQuestions;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionsRequestDTO {

    private UUID id;

    @NotNull
    private UUID examId;

    @NotNull
    private List<UUID> questionIds;
}
