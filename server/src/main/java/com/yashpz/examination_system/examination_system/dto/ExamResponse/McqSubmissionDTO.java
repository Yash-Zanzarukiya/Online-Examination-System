package com.yashpz.examination_system.examination_system.dto.ExamResponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqSubmissionDTO {
    @NotNull
    private UUID questionId;

    @NotNull
    private UUID selectedOptionId;

    @NotNull
    private int timeSpent;
}
