package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.model.McqOption;

import java.util.List;

public class ExamQuestionsMapper {

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

    public static void toExamQuestions(ExamQuestions examQuestions, List<McqOption> mcqOptions) {

    }
}
