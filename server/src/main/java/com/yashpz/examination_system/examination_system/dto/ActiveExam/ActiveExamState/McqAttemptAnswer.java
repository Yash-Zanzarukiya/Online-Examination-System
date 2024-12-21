package com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class McqAttemptAnswer implements QuestionAnswer {
    private UUID selectedOptionId;
}
