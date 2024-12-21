package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateStateDTO {
    private UUID examAttemptId;
    private UUID userId;
    private String email;
    private String candidateName;
    private ExamAttemptStatus status;
    private Integer score;
    private Integer totalMarks;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
