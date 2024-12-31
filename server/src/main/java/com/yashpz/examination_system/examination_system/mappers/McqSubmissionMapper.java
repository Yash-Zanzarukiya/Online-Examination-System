package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.McqSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.McqSubmission;
import com.yashpz.examination_system.examination_system.model.Question;

public class McqSubmissionMapper {

    public static McqSubmission toEntity(McqSubmissionRequestDTO DTO, ExamAttempt examAttempt, Question question) {
        McqSubmission mcqSubmission = new McqSubmission();
        mcqSubmission.setExamAttempt(examAttempt);
        mcqSubmission.setQuestion(question);
        mcqSubmission.setTimeSpent(0);
        mcqSubmission.setMarks(0);
        mcqSubmission.setSelectedOptionId(DTO.getSelectedOptionId());
        return mcqSubmission;
    }

    public static McqSubmission updateEntity(McqSubmission mcqSubmission, McqSubmissionRequestDTO DTO) {
        mcqSubmission.setSelectedOptionId(DTO.getSelectedOptionId());
        return mcqSubmission;
    }
}
