package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.model.Question;

import java.util.List;

public class ExamQuestionsMapper {

    public static List<ExamQuestions> toEntity(Exam exam, List<Question> questions) {
        return questions.stream()
                .map(question -> {
                    ExamQuestions examQuestions = new ExamQuestions();
                    examQuestions.setExam(exam);
                    examQuestions.setQuestion(question);
                    return examQuestions;
                })
                .toList();
    }

    public static ExamQuestionsResponseDTO toResponseDto(ExamQuestions examQuestions) {
        ExamQuestionsResponseDTO dto = new ExamQuestionsResponseDTO();
        dto.setId(examQuestions.getId());
        dto.setExamId(examQuestions.getExam().getId());
        dto.setQuestionId(examQuestions.getQuestion().getId());
        return dto;
    }

    public static List<ExamQuestionsResponseDTO> toResponseDto(List<ExamQuestions> examQuestions) {
        return examQuestions.stream()
                .map(ExamQuestionsMapper::toResponseDto)
                .toList();
    }
}
