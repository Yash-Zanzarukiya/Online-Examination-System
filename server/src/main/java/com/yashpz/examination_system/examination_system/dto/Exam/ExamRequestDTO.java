package com.yashpz.examination_system.examination_system.dto.Exam;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequestDTO {

    @NotBlank
    private String title;

    @Positive
    private int passingScore;

    private Date startDate;

    @Positive
    private int timeLimit;
}
