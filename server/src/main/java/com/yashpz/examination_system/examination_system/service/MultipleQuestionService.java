package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Question.FullQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionWithOptionsDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MultipleQuestionService {

    private final QuestionService questionService;
    private final McqOptionService mcqOptionService;

    public MultipleQuestionService(QuestionService questionService, McqOptionService mcqOptionService) {
        this.questionService = questionService;
        this.mcqOptionService = mcqOptionService;
    }

    @Transactional
    public FullQuestionDTO createQuestionWithOptions(QuestionWithOptionsDTO questionWithOptionsDTO) {
        QuestionResponseDTO question = questionService.createQuestion(questionWithOptionsDTO.getQuestion());

        List<McqOptionResponseDTO> options = mcqOptionService.createMultipleOptions(question.getId(), questionWithOptionsDTO.getOptions());

        return new FullQuestionDTO(question, options);
    }

    @Transactional
    public List<FullQuestionDTO> createQuestionWithOptions(List<QuestionWithOptionsDTO> questionWithOptionsDTO) {
        return questionWithOptionsDTO.stream()
                .map(this::createQuestionWithOptions)
                .toList();
    }

    public FullQuestionDTO getQuestionWithOptions(UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        List<McqOptionResponseDTO> options = mcqOptionService.getOptionsByQuestionId(questionId);
        return new FullQuestionDTO(question, options);
    }

    public List<FullQuestionDTO> getAllQuestionWithOptions(UUID categoryId, String difficulty,String type) {
        return questionService.getAllQuestions(categoryId, difficulty, type).stream()
                .map(question -> new FullQuestionDTO(question, mcqOptionService.getOptionsByQuestionId(question.getId())))
                .toList();
    }
}
