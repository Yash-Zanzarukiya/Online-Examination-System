package com.yashpz.examination_system.examination_system.dto.StudentProfile;

import com.yashpz.examination_system.examination_system.dto.CollegeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class StudentDataDTO {
    private UUID userId;
    private UUID studentProfileId;
    private String email;
    private String username;
    private String fullName;
    private CollegeDTO college;
    private String branch;
    private String phone;
    private Integer passout;
}
