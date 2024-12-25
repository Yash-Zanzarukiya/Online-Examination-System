package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import com.yashpz.examination_system.examination_system.model.User;

public class ExamAttemptMapper {

    public static ExamAttempt toEntity(ScheduleExam scheduledExam, User user) {
        ExamAttempt examAttempt = new ExamAttempt();
        examAttempt.setExam(scheduledExam);
        examAttempt.setUser(user);
        examAttempt.setStatus(ExamAttemptStatus.NOT_STARTED);
        examAttempt.setScore(0);
        return examAttempt;
    }
}
