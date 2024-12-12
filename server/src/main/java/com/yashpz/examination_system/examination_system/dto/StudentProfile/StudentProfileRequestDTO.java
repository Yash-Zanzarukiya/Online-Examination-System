package com.yashpz.examination_system.examination_system.dto.StudentProfile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileRequestDTO {

    @NotNull
    private UUID userId;

    @NotBlank
    @Size(min = 3, max = 100)
    private String fullName;

    @NotNull
    private UUID collegeId;

    @NotBlank
    @Size(min = 2, max = 50)
    private String branch;

    @NotBlank
    @Size(min = 10, max = 15)
    private String phone;

    @NotNull
    private Integer passout;
}
