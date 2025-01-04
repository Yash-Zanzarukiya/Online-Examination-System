package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionRequestDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class McqQuestionService {

    private final QuestionService questionService;
    private final McqOptionService mcqOptionService;

    @Transactional
    public McqQuestionResponseDTO createMcqQuestion(McqQuestionRequestDTO mcqQuestionRequestDTO) {
        QuestionResponseDTO question = questionService.createQuestion(mcqQuestionRequestDTO.getQuestion());

        List<McqOptionResponseDTO> options = mcqOptionService.createMcqOptionsForQuestion(question.getId(), mcqQuestionRequestDTO.getOptions());

        return new McqQuestionResponseDTO(question, options);
    }

    @Transactional
    public List<McqQuestionResponseDTO> createBulkMcqQuestions(List<McqQuestionRequestDTO> mcqQuestionRequestDTOs) {
        // Getting All questions
        List<QuestionRequestDTO> questionRequestDTOs = mcqQuestionRequestDTOs.stream()
                .map(McqQuestionRequestDTO::getQuestion)
                .collect(Collectors.toList());

        // Creating all questions in bulk
        List<QuestionResponseDTO> createdQuestions = questionService.createBulkQuestions(questionRequestDTOs);

        // Getting All options
        List<McqOptionRequestDTO> allOptions = new ArrayList<>();
        for (int i = 0; i < mcqQuestionRequestDTOs.size(); i++) {
            UUID questionId = createdQuestions.get(i).getId();
            List<McqOptionRequestDTO> optionsForQuestion = mcqQuestionRequestDTOs.get(i).getOptions();
            optionsForQuestion.forEach(option -> option.setQuestionId(questionId));
            allOptions.addAll(optionsForQuestion);
        }

        // Creating all options in bulk
        List<McqOptionResponseDTO> createdOptions = mcqOptionService.createBulkMcqOptions(allOptions);

        // Mapping options to their questions
        Map<UUID, List<McqOptionResponseDTO>> optionsByQuestionId = createdOptions.stream()
                .collect(Collectors.groupingBy(McqOptionResponseDTO::getQuestionId));

        // Assemble the final response
        List<McqQuestionResponseDTO> mcqQuestionResponseDTOs = createdQuestions.stream()
                .map(question -> new McqQuestionResponseDTO(
                        question,
                        optionsByQuestionId.getOrDefault(question.getId(), new ArrayList<>())
                ))
                .collect(Collectors.toList());

        return mcqQuestionResponseDTOs;
    }

    public McqQuestionResponseDTO getMcqQuestion(UUID questionId) {
        QuestionResponseDTO question = questionService.getQuestionById(questionId);
        List<McqOptionResponseDTO> options = mcqOptionService.getOptionsByQuestionId(questionId);
        return new McqQuestionResponseDTO(question, options);
    }

    public List<McqQuestionResponseDTO> getAllMcqQuestions(UUID categoryId, Difficulty difficulty) {
        // Fetching all questions
        List<QuestionResponseDTO> questions = questionService.getAllQuestions(categoryId, difficulty, QuestionType.MCQ, null);

        // Extracting question IDs
        List<UUID> questionIds = questions.stream()
                .map(QuestionResponseDTO::getId)
                .collect(Collectors.toList());

        // Fetch all options by question IDs
        List<McqOptionResponseDTO> options = mcqOptionService.getBulkOptionsByQuestionIds(questionIds);

        // Grouping options by question IDs
        Map<UUID, List<McqOptionResponseDTO>> optionsByQuestionId = options.stream()
                .collect(Collectors.groupingBy(McqOptionResponseDTO::getQuestionId));

        // Construct and return the response DTOs
        return questions.stream()
                .map(question -> new McqQuestionResponseDTO(
                        question,
                        optionsByQuestionId.getOrDefault(question.getId(), new ArrayList<>())
                ))
                .collect(Collectors.toList());
    }
}
