package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.model.McqSubmission;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class McqSubmissionAdapter implements Submission {
    private final McqSubmission mcqSubmission;

    @Override
    public UUID getQuestionId() {
        return mcqSubmission.getQuestion().getId();
    }

    @Override
    public int getMarks() {
        return mcqSubmission.getMarks();
    }

    @Override
    public int getTimeSpent() {
        return mcqSubmission.getTimeSpent();
    }
}
