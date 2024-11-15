package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.CategoryDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionMapper {

    public Question toEntity(QuestionDTO dto) {
        Question question = new Question();
        question.setQuestionText(dto.getQuestionText());
        question.setDifficulty(dto.getDifficulty());
        question.setType(dto.getType());
        if (dto.getImageUrl() != null) question.setImage(dto.getImageUrl());
        return question;
    }

    public List<Question> toEntity(List<QuestionDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    public QuestionResponseDTO toResponseDTO(Question question) {
        return new QuestionResponseDTO(
                question.getId(),
                new CategoryDTO(question.getCategory().getId(), question.getCategory().getName()),
                question.getDifficulty(),
                question.getType(),
                question.getQuestionText(),
                question.getImage(),
                question.getCorrectAnswer() != null ? question.getCorrectAnswer().getId() : null
        );
    }

    public List<QuestionResponseDTO> toResponseDTO(List<Question> questions) {
        return questions.stream().map(this::toResponseDTO).toList();
    }

    public void updateEntity(Question question, QuestionDTO dto) {
        if (dto.getQuestionText() != null) question.setQuestionText(dto.getQuestionText());
        if (dto.getDifficulty() != null) question.setDifficulty(dto.getDifficulty());
        if (dto.getType() != null) question.setType(dto.getType());
        if (dto.getImageUrl() != null) question.setImage(dto.getImageUrl());
    }
}
