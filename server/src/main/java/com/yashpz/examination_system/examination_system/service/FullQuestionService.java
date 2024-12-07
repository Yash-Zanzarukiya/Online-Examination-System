package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FullQuestionService {

    private final QuestionService questionService;
    private final McqOptionService mcqOptionService;

    public FullQuestionService(QuestionService questionService, McqOptionService mcqOptionService) {
        this.questionService = questionService;
        this.mcqOptionService = mcqOptionService;
    }

    @Transactional
    public FullQuestionResponseDTO createQuestionWithOptions(FullQuestionRequestDTO fullQuestionRequestDTO) {
        QuestionResponseDTO question = questionService.createQuestion(fullQuestionRequestDTO.getQuestion());

        List<McqOptionResponseDTO> options = mcqOptionService.createMultipleOptions(question.getId(), fullQuestionRequestDTO.getOptions());

        return new FullQuestionResponseDTO(question, options);
    }

    @Transactional
    public List<FullQuestionResponseDTO> createQuestionWithOptions(List<FullQuestionRequestDTO> fullQuestionRequestDTO) {
        return fullQuestionRequestDTO.stream()
                .map(this::createQuestionWithOptions)
                .toList();
    }

    public FullQuestionResponseDTO getFullQuestion(UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        List<McqOptionResponseDTO> options = mcqOptionService.getOptionsByQuestionId(questionId);
        return new FullQuestionResponseDTO(question, options);
    }

    public List<FullQuestionResponseDTO> getAllFullQuestions(UUID categoryId, Difficulty difficulty, QuestionType type) {
        return questionService.getAllQuestions(categoryId, difficulty, type).stream()
                .map(question -> new FullQuestionResponseDTO(question, mcqOptionService.getOptionsByQuestionId(question.getId())))
                .toList();
    }
}
