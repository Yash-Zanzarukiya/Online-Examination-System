package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Exam.AllExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllExamQuestionsMapper {

    public AllExamQuestionDTO toDTO(ExamQuestions examQuestions) {
        AllExamQuestionDTO dto = new AllExamQuestionDTO();
        dto.setId(examQuestions.getId());
        QuestionResponseDTO questionDTO = QuestionMapper.toResponseDTO(examQuestions.getQuestion());
        dto.setQuestion(questionDTO);
        return dto;
    }

    public List<AllExamQuestionDTO> toDTO(List<ExamQuestions> examQuestions) {
        return examQuestions.stream().map(this::toDTO).toList();
    }
}
