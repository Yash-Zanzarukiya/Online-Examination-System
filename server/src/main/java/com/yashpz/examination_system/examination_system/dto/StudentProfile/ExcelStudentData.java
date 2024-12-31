package com.yashpz.examination_system.examination_system.dto.StudentProfile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelStudentData {
    private String email;
    private String fullName;
    private String branch;
    private String phone;
    private Integer passout;
}
