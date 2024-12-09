package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Exam.AllExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamQuestions.ExamQuestionsResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.AllExamQuestionsMapper;
import com.yashpz.examination_system.examination_system.mappers.ExamQuestionsMapper;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ExamQuestions;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.ExamQuestionsRepository;
import com.yashpz.examination_system.examination_system.repository.ExamRepository;
import com.yashpz.examination_system.examination_system.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ExamQuestionsService {

    private final ExamQuestionsRepository examQuestionsRepository;
    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final AllExamQuestionsMapper allExamQuestionsMapper;

    public ExamQuestionsService(ExamQuestionsRepository examQuestionsRepository, ExamRepository examRepository, QuestionRepository questionRepository, AllExamQuestionsMapper allExamQuestionsMapper) {
        this.examQuestionsRepository = examQuestionsRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.allExamQuestionsMapper = allExamQuestionsMapper;
    }

    public List<ExamQuestionsResponseDTO> createExamQuestions(ExamQuestionsRequestDTO dto) {
        UUID examId = dto.getExamId();
        List<UUID> questionIds = dto.getQuestionIds();

        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));

        List<UUID> existingQuestionIds = examQuestionsRepository
                .findExistingQuestionIdsByExamIdAndQuestionIds(examId, questionIds);

        List<UUID> newQuestionIds = questionIds.stream()
                .filter(id -> !existingQuestionIds.contains(id))
                .toList();

        if (newQuestionIds.isEmpty())
            throw new ApiError(HttpStatus.BAD_REQUEST, "All questions are already added to the exam");

        // Fetch new questions in bulk
        List<Question> newQuestions = questionRepository.findAllById(newQuestionIds);
        if (newQuestions.size() != newQuestionIds.size())
            throw new ApiError(HttpStatus.NOT_FOUND, "One or more questions not found");

        List<ExamQuestions> examQuestionsList = newQuestions.stream()
                .map(question -> {
                    ExamQuestions examQuestions = new ExamQuestions();
                    examQuestions.setExam(exam);
                    examQuestions.setQuestion(question);
                    return examQuestions;
                })
                .toList();

        examQuestionsRepository.saveAll(examQuestionsList);

        return ExamQuestionsMapper.toResponseDto(examQuestionsList);
    }

    public ExamQuestionsResponseDTO getExamQuestionsById(UUID id) {
        ExamQuestions examQuestions = examQuestionsRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam question not found"));
        return ExamQuestionsMapper.toResponseDto(examQuestions);
    }

    public List<ExamQuestionsResponseDTO> getExamQuestionsByExamId(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamId(examId);
        return ExamQuestionsMapper.toResponseDto(examQuestions);
    }

    public List<AllExamQuestionDTO> getAllExamQuestions(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamIdWithFetch(examId);
        return allExamQuestionsMapper.toDTO(examQuestions);
    }

    public void removeQuestion(List<UUID> examQuestionIds) {
        int i = examQuestionsRepository.deleteByQuestionIdIn(examQuestionIds);
        System.out.println(i);
    }
}
