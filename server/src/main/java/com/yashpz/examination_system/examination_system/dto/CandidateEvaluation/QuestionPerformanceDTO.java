package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.constants.QuestionEvaluationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionPerformanceDTO {
    private UUID questionId;
    private String questionText;
    private QuestionEvaluationStatus status;
    private Integer score;
    private Integer totalMarks;
    private Integer timeTaken;
    private String category;
}
