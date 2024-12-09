package com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgrammingQuestionResponseDTO {
    private UUID id;
    private QuestionResponseDTO question;
    private String referenceAnswer;
}
