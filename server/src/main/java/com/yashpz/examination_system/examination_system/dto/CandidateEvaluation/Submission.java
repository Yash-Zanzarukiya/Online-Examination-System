package com.yashpz.examination_system.examination_system.dto.CandidateEvaluation;

import java.util.UUID;

public interface Submission {
    UUID getQuestionId();
    int getMarks();
    int getTimeSpent();
}
