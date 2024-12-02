package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Exam.ExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Exam.ExamResponseDTO;
import com.yashpz.examination_system.examination_system.model.Exam;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ExamMapper {

    public static Exam toEntity(ExamRequestDTO dto) {
        Exam exam = new Exam();
        exam.setTitle(dto.getTitle());
        exam.setPassingScore(dto.getPassingScore());
        if (dto.getStartDate()!=null)
            exam.setStartDate(dto.getStartDate());
        exam.setTimeLimit(dto.getTimeLimit());
        return exam;
    }

    public static ExamResponseDTO toResponseDTO(Exam exam) {
        return new ExamResponseDTO(
                exam.getId(),
                exam.getTitle(),
                exam.getPassingScore(),
                exam.getTimeLimit(),
                exam.getStartDate(),
                exam.getCreatedAt(),
                exam.getUpdatedAt()
        );
    }

    public static Iterable<ExamResponseDTO> toResponseDTOList(Iterable<Exam> exams) {
        List<ExamResponseDTO> responseDTOList = new ArrayList<>();
        for (Exam exam : exams)
            responseDTOList.add(ExamMapper.toResponseDTO(exam));
        return responseDTOList;
    }

    public static void updateEntity(Exam exam, ExamRequestDTO dto) {
        if (dto.getTitle()!=null)
            exam.setTitle(dto.getTitle());
        if (dto.getPassingScore()>=0)
            exam.setPassingScore(dto.getPassingScore());
        if (dto.getStartDate()!=null)
            exam.setStartDate(dto.getStartDate());
        if (dto.getTimeLimit()>=0)
            exam.setTimeLimit(dto.getTimeLimit());
    }
}
