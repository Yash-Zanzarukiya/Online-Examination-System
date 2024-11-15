package com.yashpz.examination_system.examination_system.dto.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class McqOptionResponseDTO {
    private UUID id;

    private UUID questionId;

    private String optionText;

    private String image;

    private boolean isCorrect;
}
