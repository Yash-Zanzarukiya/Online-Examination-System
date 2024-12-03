package com.yashpz.examination_system.examination_system.dto.ExamResponse;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamAttemptRequestDTO {
    @NotNull
    private UUID examId;

    @NotNull
    private UUID userId;

    @NotNull
    private ExamAttemptStatus status;

    private Date startTime;

    private Date endTime;
}
