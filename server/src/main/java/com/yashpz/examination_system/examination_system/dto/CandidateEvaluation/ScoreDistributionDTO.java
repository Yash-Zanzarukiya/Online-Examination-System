package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedMarksDTO;
import com.yashpz.examination_system.examination_system.dto.Aggregated.AggregatedScoresDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreDistributionDTO {
    private AggregatedMarksDTO aggregatedMarks;
    private AggregatedScoresDTO aggregatedScores;
}
