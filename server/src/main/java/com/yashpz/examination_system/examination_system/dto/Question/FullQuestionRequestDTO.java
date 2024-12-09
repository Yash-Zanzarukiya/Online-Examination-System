package com.yashpz.examination_system.examination_system.dto.Question;

import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestionRequestDTO {
    private QuestionRequestDTO question;
    private List<McqOptionRequestDTO> options;
}
