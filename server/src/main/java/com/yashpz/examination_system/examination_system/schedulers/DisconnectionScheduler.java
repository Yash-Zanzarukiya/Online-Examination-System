package com.yashpz.examination_system.examination_system.schedulers;

import com.yashpz.examination_system.examination_system.service.ExamSessionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DisconnectionScheduler {
    private final ExamSessionService examSessionService;

    public DisconnectionScheduler(ExamSessionService examSessionService) {
        this.examSessionService = examSessionService;
    }

//    @Scheduled(fixedRate = 60000)
    public void detectDisconnectedSessions() {}
}
