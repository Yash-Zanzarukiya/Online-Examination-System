package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.McqSubmissionDTO;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.McqOption;
import com.yashpz.examination_system.examination_system.model.McqSubmission;
import com.yashpz.examination_system.examination_system.model.Question;

public class McqSubmissionMapper {

    public static McqSubmission toEntity(McqSubmissionDTO DTO, ExamAttempt examAttempt, Question question, McqOption mcqOption) {
        McqSubmission mcqSubmission = new McqSubmission();
        mcqSubmission.setExamAttempt(examAttempt);
        mcqSubmission.setQuestion(question);
        mcqSubmission.setSelectedOption(mcqOption);
        mcqSubmission.setTimeSpent(DTO.getTimeSpent());
        return mcqSubmission;
    }

    public static McqSubmission updateEntity(McqSubmission mcqSubmission, McqSubmissionDTO DTO, McqOption mcqOption) {
        mcqSubmission.setSelectedOption(mcqOption);
        if (DTO.getTimeSpent() > 0)
            mcqSubmission.setTimeSpent(DTO.getTimeSpent() + mcqSubmission.getTimeSpent());
        return mcqSubmission;
    }
}
