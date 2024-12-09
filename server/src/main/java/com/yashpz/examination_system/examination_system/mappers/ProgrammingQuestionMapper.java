package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.ProgrammingQuestion;

import java.util.List;

public class ProgrammingQuestionMapper {
    public static ProgrammingQuestion toEntity(ProgrammingQuestionRequestDTO programmingQuestionRequestDTO) {
        ProgrammingQuestion programmingQuestion = new ProgrammingQuestion();
        programmingQuestion.setReferenceAnswer(programmingQuestionRequestDTO.getReferenceAnswer());
        return programmingQuestion;
    }

    public static List<ProgrammingQuestion> toEntity(List<ProgrammingQuestionRequestDTO> programmingQuestionRequestDTOs) {
        return programmingQuestionRequestDTOs.stream().map(ProgrammingQuestionMapper::toEntity).toList();
    }

    public static ProgrammingQuestionResponseDTO toResponseDTO(ProgrammingQuestion programmingQuestion) {
        return new ProgrammingQuestionResponseDTO(
                programmingQuestion.getId(),
                QuestionMapper.toResponseDTO(programmingQuestion.getQuestion()),
                programmingQuestion.getReferenceAnswer()
        );
    }

    public static List<ProgrammingQuestionResponseDTO> toResponseDTO(List<ProgrammingQuestion> programmingQuestions) {
        return programmingQuestions.stream().map(ProgrammingQuestionMapper::toResponseDTO).toList();
    }

    public static void updateEntity(ProgrammingQuestion programmingQuestion, ProgrammingQuestionRequestDTO programmingQuestionRequestDTO) {
        programmingQuestion.setReferenceAnswer(programmingQuestionRequestDTO.getReferenceAnswer());
    }
}
