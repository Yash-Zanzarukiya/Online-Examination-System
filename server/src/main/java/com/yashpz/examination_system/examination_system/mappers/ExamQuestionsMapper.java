package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;

import java.util.List;

public class ExamQuestionsMapper {

    public static ExamQuestionsDTO toDto(ExamQuestions examQuestions) {
        return new ExamQuestionsDTO(
                examQuestions.getId(),
                examQuestions.getExam().getId(),
                examQuestions.getQuestion().getId()
        );
    }

    public static List<ExamQuestionsDTO> toDto(List<ExamQuestions> examQuestions) {
        return examQuestions.stream().map(ExamQuestionsMapper::toDto).toList();
    }
}
