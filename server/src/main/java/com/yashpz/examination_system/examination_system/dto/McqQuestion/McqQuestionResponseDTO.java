package com.yashpz.examination_system.examination_system.dto.McqQuestion;

import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqQuestionResponseDTO {
    private QuestionResponseDTO question;
    private List<McqOptionResponseDTO> options;
}
