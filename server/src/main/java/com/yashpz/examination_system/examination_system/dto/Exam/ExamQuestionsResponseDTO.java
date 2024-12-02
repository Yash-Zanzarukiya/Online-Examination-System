package com.yashpz.examination_system.examination_system.dto.Exam;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionsResponseDTO {
    private UUID id;

    @NotNull
    private UUID examId;

    @NotNull
    private UUID questionId;
}