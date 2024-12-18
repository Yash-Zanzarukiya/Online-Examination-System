package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.ExamQuestions.FullExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.FullExamQuestionMapper;
import com.yashpz.examination_system.examination_system.mappers.ExamQuestionsMapper;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.ExamQuestionsRepository;
import com.yashpz.examination_system.examination_system.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ExamQuestionsService {

    private final ExamService examService;
    private final QuestionRepository questionRepository;
    private final ExamQuestionsRepository examQuestionsRepository;

    public ExamQuestionsService(ExamQuestionsRepository examQuestionsRepository, ExamService examService, QuestionRepository questionRepository) {
        this.examQuestionsRepository = examQuestionsRepository;
        this.examService = examService;
        this.questionRepository = questionRepository;
    }

    @Transactional
    public List<ExamQuestionsResponseDTO> addQuestions(ExamQuestionsRequestDTO dto) {
        List<UUID> questionIds = dto.getQuestionIds();

        Exam exam = examService.fetchExamById(dto.getExamId());

        //  find already added questions to the exam
        List<UUID> existingQuestionIds = examQuestionsRepository
                .findExistingQuestionIdsByExamIdAndQuestionIds(exam.getId(), questionIds);

        List<UUID> newQuestionIds = questionIds.stream()
                .filter(id -> !existingQuestionIds.contains(id))
                .toList();

        if (newQuestionIds.isEmpty())
            throw new ApiError(HttpStatus.BAD_REQUEST, "All questions are already added to the exam");

        // Fetching new questions in bulk
        List<Question> newQuestions = questionRepository.findAllById(newQuestionIds);
        if (newQuestions.size() != newQuestionIds.size())
            throw new ApiError(HttpStatus.NOT_FOUND, "One or more questions not found");

        // Mapping and saving entities
        List<ExamQuestions> examQuestionsList = ExamQuestionsMapper.toEntity(exam, newQuestions);

        examQuestionsRepository.saveAll(examQuestionsList);

        return ExamQuestionsMapper.toResponseDto(examQuestionsList);
    }

    @Transactional
    public void removeQuestions(List<UUID> examQuestionIds) {
        int deletedCount = examQuestionsRepository.deleteByExamQuestionIds(examQuestionIds);
        if (deletedCount != examQuestionIds.size())
            throw new ApiError(HttpStatus.BAD_REQUEST, "Some questions could not be deleted");
    }

    public ExamQuestionsResponseDTO getExamQuestionById(UUID id) {
        ExamQuestions examQuestions = examQuestionsRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam question not found"));
        return ExamQuestionsMapper.toResponseDto(examQuestions);
    }

    public List<ExamQuestionsResponseDTO> getExamQuestionsByExamId(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamId(examId);
        return ExamQuestionsMapper.toResponseDto(examQuestions);
    }

    public List<FullExamQuestionDTO> getFullExamQuestionsByExamId(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamIdWithFetch(examId);
        return FullExamQuestionMapper.toDTO(examQuestions);
    }

    // <--------------- Helpers --------------->

    public ExamQuestions fetchExamQuestionById(UUID id) {
        return examQuestionsRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam question not found"));
    }

    public Boolean isExistsByExamIdAndQuestionId(UUID examId, UUID questionId) {
         return examQuestionsRepository.existsByExamIdAndQuestionId(examId, questionId);
    }
}
