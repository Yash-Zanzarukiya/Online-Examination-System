package com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState;

import com.yashpz.examination_system.examination_system.constants.QuestionAttemptStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveExamQuestionsState {
    private UUID questionId;
    private QuestionAttemptStatus status;
    private UUID selectedOptionId;
    private String submittedCode;

    public ActiveExamQuestionsState(UUID questionId, String status, UUID selectedOptionId) {
        this.questionId = questionId;
        this.status = QuestionAttemptStatus.valueOf(status);
        this.selectedOptionId = selectedOptionId;
    }

    public ActiveExamQuestionsState(UUID questionId, String status, String submittedCode) {
        this.questionId = questionId;
        this.status = QuestionAttemptStatus.valueOf(status);
        this.submittedCode = submittedCode;
    }
}
