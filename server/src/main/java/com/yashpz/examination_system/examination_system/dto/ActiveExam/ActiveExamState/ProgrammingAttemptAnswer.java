package com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingAttemptAnswer implements QuestionAnswer{
    private String submittedCode;
}
