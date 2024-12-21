package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class ProgrammingSubmissionAdapter implements Submission {
    private final ProgrammingSubmission programmingSubmission;

    @Override
    public UUID getQuestionId() {
        return programmingSubmission.getQuestion().getId();
    }

    @Override
    public int getMarks() {
        return programmingSubmission.getMarks();
    }

    @Override
    public int getTimeSpent() {
        return programmingSubmission.getTimeSpent();
    }
}
