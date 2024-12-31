package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.ProgrammingSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import com.yashpz.examination_system.examination_system.model.Question;

public class ProgrammingSubmissionMapper {

    public static ProgrammingSubmission toEntity(ProgrammingSubmissionRequestDTO DTO, ExamAttempt examAttempt, Question question) {
        ProgrammingSubmission programmingSubmission = new ProgrammingSubmission();
        programmingSubmission.setExamAttempt(examAttempt);
        programmingSubmission.setQuestion(question);
        programmingSubmission.setSubmittedCode(DTO.getSubmittedCode());
        programmingSubmission.setTimeSpent(0);
        programmingSubmission.setMarks(0);
        programmingSubmission.setProgrammingLanguage(DTO.getProgrammingLanguage());
        return programmingSubmission;
    }

    public static ProgrammingSubmission updateEntity(ProgrammingSubmission programmingSubmission, ProgrammingSubmissionRequestDTO DTO) {
        programmingSubmission.setSubmittedCode(DTO.getSubmittedCode());
        programmingSubmission.setProgrammingLanguage(DTO.getProgrammingLanguage());
        return programmingSubmission;
    }
}
