package com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveExamQuestionsDTO {
    private ActiveExamQuestion question;
    private List<ActiveExamMcqOption> options;
}
