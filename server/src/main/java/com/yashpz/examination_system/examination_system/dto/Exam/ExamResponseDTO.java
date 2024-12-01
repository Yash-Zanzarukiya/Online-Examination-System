package com.yashpz.examination_system.examination_system.dto.Exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponseDTO {
    private UUID id;
    private String title;
    private int passingScore;
    private int timeLimit;
    private Date startDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
