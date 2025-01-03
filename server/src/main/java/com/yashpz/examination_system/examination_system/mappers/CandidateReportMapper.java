package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.constants.QuestionEvaluationStatus;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.CandidateEvaluation.*;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.model.McqSubmission;
import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import com.yashpz.examination_system.examination_system.model.Question;


public class CandidateReportMapper {

    public static QuestionPerformanceDTO toQuestionPerformanceDTO(ExamQuestions eq, Submission submission) {
        QuestionPerformanceDTO dto = new QuestionPerformanceDTO();

        Question question = eq.getQuestion();
        dto.setQuestionId(question.getId());
        dto.setQuestionText(question.getQuestionText());
        dto.setTotalMarks(question.getMarks());
        if(question.getType() == QuestionType.MCQ)
            dto.setCategory(question.getCategory().getName());

        dto.setScore(submission != null ? submission.getMarks() : 0);
        dto.setTimeTaken(submission != null ? submission.getTimeSpent() : 0);
        QuestionEvaluationStatus status = submission == null ? QuestionEvaluationStatus.NOT_ANSWERED : getQuestionEvaluationStatus(dto.getTotalMarks(), dto.getScore());
        dto.setStatus(status);

        return dto;
    }

    public static DetailedMcqSubmissionDTO toDetailedMcqSubmissionDTO(McqQuestionResponseDTO question, McqSubmission submission) {
        DetailedMcqSubmissionDTO dto = new DetailedMcqSubmissionDTO();
        dto.setQuestion(question);
        dto.setSelectedOptionId(submission.getSelectedOptionId());
        dto.setScore(submission.getMarks());
        return dto;
    }

    public static DetailedProgrammingSubmissionDTO toDetailedProgrammingSubmissionDTO(ProgrammingQuestionResponseDTO question, ProgrammingSubmission submission) {
        DetailedProgrammingSubmissionDTO dto = new DetailedProgrammingSubmissionDTO();
        dto.setId(submission.getId());
        dto.setQuestion(question);
        dto.setScore(submission.getMarks());
        dto.setSubmittedCode(submission.getSubmittedCode());
        return dto;
    }

    // <--------------- Helpers --------------->

    private static QuestionEvaluationStatus getQuestionEvaluationStatus(int total, int score) {
        if(score == total)
            return QuestionEvaluationStatus.CORRECT;
        else if (score > 0 && score < total)
            return QuestionEvaluationStatus.PARTIALLY_CORRECT;
        else
            return QuestionEvaluationStatus.INCORRECT;
    }

}
