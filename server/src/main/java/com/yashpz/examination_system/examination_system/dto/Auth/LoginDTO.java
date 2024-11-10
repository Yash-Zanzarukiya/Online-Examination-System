package com.yashpz.examination_system.examination_system.dto.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank
    @Size(min = 3)
    private String identifier;

    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
}
