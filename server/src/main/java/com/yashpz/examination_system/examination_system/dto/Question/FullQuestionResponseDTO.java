package com.yashpz.examination_system.examination_system.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FullQuestionResponseDTO {
    private QuestionResponseDTO question;
    private List<McqOptionResponseDTO> options;
}
