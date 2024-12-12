package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamResponseDTO;
import com.yashpz.examination_system.examination_system.model.Exam;

import java.util.List;

public class ExamMapper {

    public static Exam toEntity(ExamRequestDTO dto) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setPassingScore(dto.getPassingScore());
        exam.setTimeLimit(dto.getTimeLimit());
        return exam;
    }

    public static List<Exam> toEntity(List<ExamRequestDTO> DTOs) {
        return DTOs.stream().map(ExamMapper::toEntity).toList();
    }

    public static ExamResponseDTO toResponseDTO(Exam exam) {
        return new ExamResponseDTO(
                exam.getId(),
                exam.getTitle(),
                exam.getPassingScore(),
                exam.getTimeLimit(),
                exam.getCreatedAt(),
                exam.getUpdatedAt()
        );
    }

    public static List<ExamResponseDTO> toResponseDTO(List<Exam> exams) {
        return exams.stream().map(ExamMapper::toResponseDTO).toList();
    }

    public static void updateEntity(Exam exam, ExamRequestDTO dto) {
        if (dto.getTitle()!=null)
            exam.setTitle(dto.getTitle());
        if (dto.getPassingScore()>=0)
            exam.setPassingScore(dto.getPassingScore());
        if (dto.getTimeLimit()>=0)
            exam.setTimeLimit(dto.getTimeLimit());
    }
}
