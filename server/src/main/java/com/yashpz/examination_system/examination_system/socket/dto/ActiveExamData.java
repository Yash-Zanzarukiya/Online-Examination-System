package com.yashpz.examination_system.examination_system.socket.dto;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiveExamData {
    private ScheduledExamStatus status;
    private LocalDateTime startingAt;
}
