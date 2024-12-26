package com.yashpz.examination_system.examination_system.dto.ActiveExam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttemptRequestDTO {

    @NotBlank
    private String identifier;

    @NotBlank 
    private String password;

    @NotNull
    private UUID scheduledExamId;
}
