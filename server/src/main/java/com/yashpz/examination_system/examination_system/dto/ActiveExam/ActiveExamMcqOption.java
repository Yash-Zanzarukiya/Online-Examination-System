package com.yashpz.examination_system.examination_system.dto.ActiveExam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveExamMcqOption {
    private UUID id;
    private String optionText;
    private String image;
}
