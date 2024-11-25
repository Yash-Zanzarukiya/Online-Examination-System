package com.yashpz.examination_system.examination_system.dto.User;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileResponseDTO {

    private UUID id;

    private UUID userId;

    private String fullName;

    private CollegeDTO college;

    private String branch;

    private String phone;

    private Integer passout;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
