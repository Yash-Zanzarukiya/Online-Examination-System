package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedMcqSubmissionDTO {
    private McqQuestionResponseDTO question;
    private UUID selectedOptionId;
    private Integer score;
}
