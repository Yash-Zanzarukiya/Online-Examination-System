package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamMapper;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.repository.ExamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Transactional
    public ExamResponseDTO createExam(ExamRequestDTO dto) {
        Exam exam = ExamMapper.toEntity(dto);
        examRepository.save(exam);
        return ExamMapper.toResponseDTO(exam);
    }

    public ExamResponseDTO getExamById(UUID examId) {
        Exam exam = fetchExamById(examId);
        return ExamMapper.toResponseDTO(exam);
    }

    public List<ExamResponseDTO> getAllExams(boolean drafted) {
        if (drafted) return getAllDraftedExams();

        List<Exam> exams = examRepository.findAll();
        return ExamMapper.toResponseDTO(exams);
    }

    public List<ExamResponseDTO> getAllDraftedExams() {
        List<Exam> exams = examRepository.findAllDraftedExams();
        return ExamMapper.toResponseDTO(exams);
    }

    @Transactional
    public ExamResponseDTO updateExam(UUID examId, ExamRequestDTO dto) {
        Exam exam = fetchExamById(examId);

        ExamMapper.updateEntity(exam, dto);
        examRepository.save(exam);

        return ExamMapper.toResponseDTO(exam);
    }

    @Transactional
    public void deleteExam(UUID examId) {
        Exam exam = fetchExamById(examId);
        examRepository.delete(exam);
    }

    // <----------- Helpers ----------->

    public Exam fetchExamById(UUID examId) {
        return examRepository.findById(examId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));
    }
}
