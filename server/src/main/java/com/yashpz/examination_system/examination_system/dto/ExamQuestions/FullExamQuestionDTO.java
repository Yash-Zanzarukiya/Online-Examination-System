package com.yashpz.examination_system.examination_system.dto.ExamQuestions;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullExamQuestionDTO {
    private UUID id;
    private QuestionResponseDTO question;
}