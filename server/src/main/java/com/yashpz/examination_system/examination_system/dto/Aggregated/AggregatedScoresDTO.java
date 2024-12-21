package com.yashpz.examination_system.examination_system.dto.Aggregated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregatedScoresDTO {
    private Long totalScore;
    private Long mcqQuestionScore;
    private Long programmingQuestionScore;
    private Long easyQuestionScore;
    private Long mediumQuestionScore;
    private Long hardQuestionScore;
    private Long aptitudeMcqScore;
    private Long technicalMcqScore;
    private Long programmingMcqScore;
}
