package com.yashpz.examination_system.examination_system.dto.ScheduleExam;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
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
public class ScheduleExamRequestDTO {
    @NotNull(groups = ValidationGroups.Create.class)
    private String name;

    @NotNull(groups = ValidationGroups.Create.class)
    private UUID examId;

    private UUID collegeId;

    private ScheduledExamStatus status;

    @NotNull(groups = {ValidationGroups.Create.class})
    private LocalDateTime startingAt;
}
