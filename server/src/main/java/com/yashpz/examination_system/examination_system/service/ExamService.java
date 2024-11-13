package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamMapper;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.repository.ExamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExamService {

    private final ExamRepository examRepository;

    public ExamService(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public ExamResponseDTO createExam(ExamRequestDTO dto) {
        Exam exam = ExamMapper.toEntity(dto);
        return ExamMapper.toResponseDTO(examRepository.save(exam));
    }

    public ExamResponseDTO getExamById(UUID examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));
        return ExamMapper.toResponseDTO(exam);
    }

    public Iterable<ExamResponseDTO> getAllExams() {
        return ExamMapper.toResponseDTOList(examRepository.findAll());
    }

    public ExamResponseDTO updateExam(UUID examId, ExamRequestDTO dto) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));
        ExamMapper.updateEntity(exam, dto);
        return ExamMapper.toResponseDTO(examRepository.save(exam));
    }

    public void deleteExam(UUID examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam not found"));
        examRepository.delete(exam);
    }
}
