package com.yashpz.examination_system.examination_system.dto.ActiveExam;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveExamQuestion {
    private UUID id;
    private QuestionType type;
    private Difficulty difficulty;
    private String questionText;
    private String image;
}
