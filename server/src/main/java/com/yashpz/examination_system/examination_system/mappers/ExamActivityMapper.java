package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.constants.ActiveExamActivities;
import com.yashpz.examination_system.examination_system.dto.ExamActivity.ExamActivityRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamActivity.ExamActivityResponseDTO;
import com.yashpz.examination_system.examination_system.model.ExamActivity;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;

import java.util.List;
import java.util.UUID;

public class ExamActivityMapper {

    public static ExamActivity toEntity(ExamActivityRequestDTO dto, ExamAttempt examAttempt) {
        ExamActivity examActivity = new ExamActivity();
        examActivity.setExamAttempt(examAttempt);
        examActivity.setName(dto.getName());
        examActivity.setDescription(dto.getDescription());
        return examActivity;
    }

    public static ExamActivity toEntity(ExamAttempt examAttempt, ActiveExamActivities activity, String description) {
        ExamActivity examActivity = new ExamActivity();
        examActivity.setExamAttempt(examAttempt);
        examActivity.setName(activity);
        examActivity.setDescription(description);
        return examActivity;
    }

    public static List<ExamActivity> toEntity(List<ExamActivityRequestDTO> dtos, ExamAttempt examAttempt) {
        return dtos.stream().map(dto -> toEntity(dto, examAttempt)).toList();
    }

    public static ExamActivityResponseDTO toResponseDTO(ExamActivity examActivity, UUID examAttemptId) {
        ExamActivityResponseDTO dto = new ExamActivityResponseDTO();
        dto.setId(examActivity.getId());
        dto.setExamAttemptId(examAttemptId);
        dto.setName(examActivity.getName());
        dto.setDescription(examActivity.getDescription());
        dto.setCreatedAt(examActivity.getCreatedAt());
        return dto;
    }

    public static List<ExamActivityResponseDTO> toResponseDTO(List<ExamActivity> examActivities, UUID examAttemptId) {
        return examActivities.stream().map(examActivity -> toResponseDTO(examActivity, examAttemptId)).toList();
    }
}
