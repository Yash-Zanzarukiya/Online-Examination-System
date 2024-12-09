package com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingQuestionRequestDTO {
    private QuestionRequestDTO question;

    private String referenceAnswer;
}
