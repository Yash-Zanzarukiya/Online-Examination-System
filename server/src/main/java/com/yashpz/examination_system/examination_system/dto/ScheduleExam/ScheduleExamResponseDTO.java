package com.yashpz.examination_system.examination_system.dto.ScheduleExam;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleExamResponseDTO {
    private UUID id;
    private UUID examId;
    private String name;
    private UUID collegeId;
    private ScheduledExamStatus status;
    private String startingAt;
    private String createdAt;
    private String updatedAt;
}
