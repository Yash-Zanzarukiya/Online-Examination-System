package com.yashpz.examination_system.examination_system.dto.ExamActivity;

import com.yashpz.examination_system.examination_system.constants.ActiveExamActivities;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExamActivityRequestDTO {
    private UUID examAttemptId;

    @NotNull
    private ActiveExamActivities name;

    private String description;
}
