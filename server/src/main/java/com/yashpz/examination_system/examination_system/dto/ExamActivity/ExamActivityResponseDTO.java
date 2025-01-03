package com.yashpz.examination_system.examination_system.dto.ExamActivity;

import com.yashpz.examination_system.examination_system.constants.ActiveExamActivities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamActivityResponseDTO {
    private UUID id;
    private UUID examAttemptId;
    private ActiveExamActivities name;
    private String description;
    private LocalDateTime createdAt;
}
