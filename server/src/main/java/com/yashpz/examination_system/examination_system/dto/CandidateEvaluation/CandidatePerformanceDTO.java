package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidatePerformanceDTO {
    private UUID examAttemptId;
}
