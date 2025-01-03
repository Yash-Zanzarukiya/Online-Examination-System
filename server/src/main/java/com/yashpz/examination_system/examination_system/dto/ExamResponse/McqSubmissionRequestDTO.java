package com.yashpz.examination_system.examination_system.dto.ExamResponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqSubmissionRequestDTO {
    @NotNull
    private UUID questionId;

    @NotNull
    private UUID selectedOptionId;

    private Integer questionNumber;
}
