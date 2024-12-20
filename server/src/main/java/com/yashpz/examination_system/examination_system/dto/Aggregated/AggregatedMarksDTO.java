package com.yashpz.examination_system.examination_system.dto.Aggregated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregatedMarksDTO {
    private Long totalMarks;
    private Long mcqQuestionTotalMarks;
    private Long programmingQuestionTotalMarks;
    private Long easyQuestionTotalMarks;
    private Long mediumQuestionTotalMarks;
    private Long hardQuestionTotalMarks;
    private Long aptitudeMcqTotalMarks;
    private Long technicalMcqTotalMarks;
    private Long programmingMcqTotalMarks;
}
