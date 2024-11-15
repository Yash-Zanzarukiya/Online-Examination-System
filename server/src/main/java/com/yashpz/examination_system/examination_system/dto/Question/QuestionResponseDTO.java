package com.yashpz.examination_system.examination_system.dto.Question;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDTO {
    private UUID id;

    private CategoryDTO category;

    private Difficulty difficulty;

    private QuestionType type;

    private String questionText;

    private String image;

    private UUID correctAnswerId;
}
