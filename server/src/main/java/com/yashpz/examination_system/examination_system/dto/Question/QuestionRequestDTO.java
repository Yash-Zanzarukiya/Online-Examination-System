package com.yashpz.examination_system.examination_system.dto.Question;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionRequestDTO {
    private UUID id;

    private UUID categoryId;

    @NotNull
    private Difficulty difficulty;

    @NotNull
    private QuestionType type;

    @NotBlank
    private String questionText;

    private Integer marks;

    private String imageUrl;
    private MultipartFile imageFile;
}
