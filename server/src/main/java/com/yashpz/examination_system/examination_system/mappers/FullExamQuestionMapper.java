package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ExamQuestions.FullExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;

import java.util.List;

public class FullExamQuestionMapper {

    public static FullExamQuestionDTO toDTO(ExamQuestions examQuestions) {
        FullExamQuestionDTO dto = new FullExamQuestionDTO();
        dto.setId(examQuestions.getId());
        QuestionResponseDTO questionDTO = QuestionMapper.toResponseDTO(examQuestions.getQuestion());
        dto.setQuestion(questionDTO);
        return dto;
    }

    public static List<FullExamQuestionDTO> toDTO(List<ExamQuestions> examQuestions) {
        return examQuestions.stream().map(FullExamQuestionMapper::toDTO).toList();
    }
}
