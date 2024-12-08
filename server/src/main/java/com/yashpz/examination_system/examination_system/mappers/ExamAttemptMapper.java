package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.User;

public class ExamAttemptMapper {

    public static ExamAttempt toEntity(ExamAttemptRequestDTO examAttemptRequestDTO, Exam exam, User user) {
        ExamAttempt examAttempt = new ExamAttempt();
//        examAttempt.setExam();
        examAttempt.setUser(user);
        examAttempt.setStatus(examAttemptRequestDTO.getStatus());
        examAttempt.setStartTime(examAttemptRequestDTO.getStartTime());
        return examAttempt;
    }

    public static ExamAttempt updateEntity(ExamAttempt examAttempt, ExamAttemptRequestDTO examAttemptRequestDTO) {
        examAttempt.setStatus(examAttemptRequestDTO.getStatus());
        if(examAttemptRequestDTO.getEndTime() != null)
            examAttempt.setEndTime(examAttemptRequestDTO.getEndTime());
        return examAttempt;
    }
}
