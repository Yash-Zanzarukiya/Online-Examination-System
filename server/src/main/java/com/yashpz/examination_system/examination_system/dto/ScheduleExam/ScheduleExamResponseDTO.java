package com.yashpz.examination_system.examination_system.dto.ScheduleExam;

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
    private UUID collegeId;
    private String startingAt;
    private String createdAt;
    private String updatedAt;
}
