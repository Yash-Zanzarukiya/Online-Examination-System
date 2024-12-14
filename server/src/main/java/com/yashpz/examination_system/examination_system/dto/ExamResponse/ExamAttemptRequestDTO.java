package com.yashpz.examination_system.examination_system.dto.ExamResponse;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.constants.ValidationGroups;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttemptRequestDTO {
    @NotNull(groups = ValidationGroups.Create.class)
    private String visitorId;

    @NotNull(groups = ValidationGroups.Create.class)
    private UUID examId;

    @NotNull(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private ExamAttemptStatus status;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
