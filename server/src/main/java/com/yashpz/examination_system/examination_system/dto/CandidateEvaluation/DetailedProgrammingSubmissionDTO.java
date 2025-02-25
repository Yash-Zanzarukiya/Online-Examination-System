package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailedProgrammingSubmissionDTO {
    private UUID id;
    private ProgrammingQuestionResponseDTO question;
    private String submittedCode;
    private int score;
}
