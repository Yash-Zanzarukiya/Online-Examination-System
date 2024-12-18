package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.ProgrammingSubmissionDTO;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import com.yashpz.examination_system.examination_system.model.Question;

public class ProgrammingSubmissionMapper {

    public static ProgrammingSubmission toEntity(ProgrammingSubmissionDTO DTO, ExamAttempt examAttempt, Question question) {
        ProgrammingSubmission programmingSubmission = new ProgrammingSubmission();
        programmingSubmission.setExamAttempt(examAttempt);
        programmingSubmission.setQuestion(question);
        programmingSubmission.setSubmittedCode(DTO.getSubmittedCode());
        programmingSubmission.setProgrammingLanguage(DTO.getProgrammingLanguage());
        programmingSubmission.setTimeSpent(DTO.getTimeSpent());
        return programmingSubmission;
    }

    public static ProgrammingSubmission updateEntity(ProgrammingSubmission programmingSubmission, ProgrammingSubmissionDTO DTO) {
        programmingSubmission.setSubmittedCode(DTO.getSubmittedCode());
        programmingSubmission.setProgrammingLanguage(DTO.getProgrammingLanguage());
        if (DTO.getTimeSpent() > 0)
            programmingSubmission.setTimeSpent(DTO.getTimeSpent() + programmingSubmission.getTimeSpent());
        return programmingSubmission;
    }
}
