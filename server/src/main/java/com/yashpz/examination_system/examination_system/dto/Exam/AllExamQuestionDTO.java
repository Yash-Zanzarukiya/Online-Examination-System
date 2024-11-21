package com.yashpz.examination_system.examination_system.dto.Exam;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllExamQuestionDTO {
    private UUID id;
    private QuestionResponseDTO question;
}