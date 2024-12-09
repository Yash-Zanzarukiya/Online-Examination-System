package com.yashpz.examination_system.examination_system.dto.McqQuestion;

import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqQuestionRequestDTO {
    private QuestionRequestDTO question;
    private List<McqOptionRequestDTO> options;
}
