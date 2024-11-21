package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Exam.AllExamQuestionDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamQuestionsDTO;
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

    public ExamQuestionsDTO createExamQuestion(ExamQuestionsDTO dto) {
        if (examQuestionsRepository.existsByExamIdAndQuestionId(dto.getExamId(), dto.getQuestionId()))
            throw new ApiError(HttpStatus.BAD_REQUEST, "Question already added to exam");

        Exam exam = examRepository.findById(dto.getExamId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));

        Question question = questionRepository.findById(dto.getQuestionId())
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Question not found"));

        ExamQuestions examQuestions = new ExamQuestions();
        examQuestions.setExam(exam);
        examQuestions.setQuestion(question);

        examQuestionsRepository.save(examQuestions);

        return ExamQuestionsMapper.toDto(examQuestions);
    }

    public ExamQuestionsDTO getExamQuestionsById(UUID id) {
        ExamQuestions examQuestions = examQuestionsRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam question not found"));
        return ExamQuestionsMapper.toDto(examQuestions);
    }

    public List<ExamQuestionsDTO> getExamQuestionsByExamId(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamId(examId);
        return ExamQuestionsMapper.toDto(examQuestions);
    }

    public List<AllExamQuestionDTO> getAllExamQuestions(UUID examId) {
        List<ExamQuestions> examQuestions = examQuestionsRepository.findByExamIdWithFetch(examId);
        return allExamQuestionsMapper.toDTO(examQuestions);
    }

    public void removeQuestion(UUID id) {
        if (!examQuestionsRepository.existsById(id))
            throw new ApiError(HttpStatus.NOT_FOUND, "Exam question not found");

        examQuestionsRepository.deleteById(id);
    }

}
