package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionResponseDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ProgrammingQuestionMapper;
import com.yashpz.examination_system.examination_system.model.ProgrammingQuestion;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.ProgrammingQuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProgrammingQuestionService {

    private final ProgrammingQuestionRepository programmingQuestionRepository;
    private final QuestionService questionService;

    public ProgrammingQuestionService(QuestionService questionService, ProgrammingQuestionRepository programmingQuestionRepository) {
        this.programmingQuestionRepository = programmingQuestionRepository;
        this.questionService = questionService;
    }

    @Transactional
    public ProgrammingQuestionResponseDTO createProgrammingQuestion(ProgrammingQuestionRequestDTO programmingQuestionRequestDTO) {
        QuestionResponseDTO questionResponseDTO = questionService.createQuestion(programmingQuestionRequestDTO.getQuestion());

        ProgrammingQuestion programmingQuestion = ProgrammingQuestionMapper.toEntity(programmingQuestionRequestDTO);

        programmingQuestion.setQuestion(questionService.fetchQuestionById(questionResponseDTO.getId()));

        ProgrammingQuestion programmingQuestionEntity = programmingQuestionRepository.save(programmingQuestion);

        return ProgrammingQuestionMapper.toResponseDTO(programmingQuestionEntity);
    }

    //  TODO : Optimize this code
    @Transactional
    public List<ProgrammingQuestionResponseDTO> createBulkProgrammingQuestions(List<ProgrammingQuestionRequestDTO> programmingQuestionRequestDTOs) {
        List<QuestionRequestDTO> questionRequestDTOs = programmingQuestionRequestDTOs.stream().map(ProgrammingQuestionRequestDTO::getQuestion).toList();

        List<QuestionResponseDTO> questionResponseDTOs = questionService.createBulkQuestions(questionRequestDTOs);

        List<ProgrammingQuestion> programmingQuestions = ProgrammingQuestionMapper.toEntity(programmingQuestionRequestDTOs);

        for (int i = 0; i < programmingQuestions.size(); i++) {
            Question question = questionService.fetchQuestionById(questionResponseDTOs.get(i).getId());
            programmingQuestions.get(i).setQuestion(question);
        }

        List<ProgrammingQuestion> programmingQuestionEntities = programmingQuestionRepository.saveAll(programmingQuestions);

        return ProgrammingQuestionMapper.toResponseDTO(programmingQuestionEntities);
    }

    public ProgrammingQuestionResponseDTO getProgrammingQuestionById(UUID id) {
        ProgrammingQuestion programmingQuestion = fetchProgrammingQuestionById(id);
        return ProgrammingQuestionMapper.toResponseDTO(programmingQuestion);
    }

    public ProgrammingQuestionResponseDTO getProgrammingQuestionByQuestionId(UUID questionId) {
        ProgrammingQuestion programmingQuestion = fetchProgrammingQuestionByQuestionId(questionId);
        return ProgrammingQuestionMapper.toResponseDTO(programmingQuestion);
    }

    public List<ProgrammingQuestionResponseDTO> getAllProgrammingQuestions(Difficulty difficulty) {
        List<ProgrammingQuestion> programmingQuestions = programmingQuestionRepository.findAllByQuestionDifficultyAndQuestionType(difficulty, QuestionType.PROGRAMMING);
        return ProgrammingQuestionMapper.toResponseDTO(programmingQuestions);
    }

    @Transactional
    public ProgrammingQuestionResponseDTO updateProgrammingQuestion(UUID id, ProgrammingQuestionRequestDTO programmingQuestionRequestDTO) {
        ProgrammingQuestion programmingQuestion = fetchProgrammingQuestionById(id);

        ProgrammingQuestionMapper.updateEntity(programmingQuestion, programmingQuestionRequestDTO);

        ProgrammingQuestion updatedProgrammingQuestion = programmingQuestionRepository.save(programmingQuestion);

        return ProgrammingQuestionMapper.toResponseDTO(updatedProgrammingQuestion);
    }

    @Transactional
    public void deleteProgrammingQuestion(UUID id) {
        ProgrammingQuestion programmingQuestion = fetchProgrammingQuestionById(id);
        programmingQuestion.setReferenceAnswer("");
        programmingQuestionRepository.save(programmingQuestion);
    }

    // <----------- Helpers ----------->

    public ProgrammingQuestion fetchProgrammingQuestionById(UUID id) {
        return programmingQuestionRepository.findById(id).orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND,"Programming Question not found"));
    }

    public ProgrammingQuestion fetchProgrammingQuestionByQuestionId(UUID questionId) {
        return programmingQuestionRepository.findByQuestionId(questionId);
    }
}
