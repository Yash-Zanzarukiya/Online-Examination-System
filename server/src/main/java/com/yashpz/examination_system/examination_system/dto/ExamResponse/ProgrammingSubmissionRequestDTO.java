package com.yashpz.examination_system.examination_system.dto.ExamResponse;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProgrammingSubmissionRequestDTO {
    @NotNull
    private UUID questionId;

    @NotNull
    private String submittedCode;

    private String programmingLanguage;
}
