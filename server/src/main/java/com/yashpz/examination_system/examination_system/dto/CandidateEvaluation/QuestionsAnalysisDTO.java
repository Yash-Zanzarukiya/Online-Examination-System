package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsAnalysisDTO {
    private String sectionName;
    private Integer totalQuestions;
    private Integer attemptedQuestions;
    private Integer score;
    private Integer totalMarks;
    private Integer timeTaken;
    private List<QuestionPerformanceDTO> questionPerformances;
}
