package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.Question;

import java.util.List;

public class QuestionMapper {

    public static Question toEntity(QuestionRequestDTO dto) {
        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setDifficulty(dto.getDifficulty());
        question.setType(dto.getType());
        question.setMarks(dto.getMarks());
        if (dto.getImageUrl() != null) question.setImage(dto.getImageUrl());
        return question;
    }

    public static List<Question> toEntity(List<QuestionRequestDTO> DTOs) {
        return DTOs.stream().map(QuestionMapper::toEntity).toList();
    }

    public static QuestionResponseDTO toResponseDTO(Question question) {
        return new QuestionResponseDTO(
                question.getId(),
                CategoryMapper.toDTO(question.getCategory()),
                question.getDifficulty(),
                question.getType(),
                question.getQuestionText(),
                question.getImage(),
                question.getMarks()
        );
    }

    public static List<QuestionResponseDTO> toResponseDTO(List<Question> questions) {
        return questions.stream().map(QuestionMapper::toResponseDTO).toList();
    }

    public static void updateEntity(Question question, QuestionRequestDTO dto) {
        if (dto.getQuestionText() != null) question.setQuestionText(dto.getQuestionText());
        if (dto.getDifficulty() != null) question.setDifficulty(dto.getDifficulty());
        if (dto.getType() != null) question.setType(dto.getType());
        if (dto.getImageUrl() != null) question.setImage(dto.getImageUrl());
        if (dto.getMarks()!= null) question.setMarks(dto.getMarks());
    }
}
