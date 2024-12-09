package com.yashpz.examination_system.examination_system.dto.McqOption;

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
public class McqOptionRequestDTO {
    private UUID id;

    private UUID questionId;

    @NotBlank
    private String optionText;

    private String imageUrl;

    private MultipartFile imageFile;

    @NotNull
    private Boolean isCorrect;
}
