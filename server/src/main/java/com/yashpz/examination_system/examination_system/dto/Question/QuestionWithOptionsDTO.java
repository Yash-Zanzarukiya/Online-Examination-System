package com.yashpz.examination_system.examination_system.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionWithOptionsDTO {
    private QuestionDTO question;
    private List<McqOptionDTO> options;
}
