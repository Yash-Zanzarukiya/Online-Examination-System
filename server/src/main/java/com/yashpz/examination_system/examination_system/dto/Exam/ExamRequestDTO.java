package com.yashpz.examination_system.examination_system.dto.Exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequestDTO {

    @NotBlank
    private String title;

    @Positive
    private int passingScore;

    @Positive
    private int timeLimit;
}
