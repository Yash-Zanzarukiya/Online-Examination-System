package com.yashpz.examination_system.examination_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private UUID id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
}
