package com.yashpz.examination_system.examination_system.socket.dto;

import com.yashpz.examination_system.examination_system.constants.ExamSessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketConnectionDTO {
    private ExamSessionType sessionType;
    private ActiveExamData activeExamData;
    private Integer timeRemaining;
    private String message;
}
