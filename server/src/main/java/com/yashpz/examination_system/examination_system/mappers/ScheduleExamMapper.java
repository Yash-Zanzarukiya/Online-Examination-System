package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;

import java.util.List;

public class ScheduleExamMapper {
    public static ScheduleExam toEntity(ScheduleExamRequestDTO scheduleExamRequestDTO, Exam exam, College college) {
        ScheduleExam scheduleExam = new ScheduleExam();
        scheduleExam.setName(scheduleExamRequestDTO.getName());
        scheduleExam.setExam(exam);
        scheduleExam.setCollege(college);
        scheduleExam.setStatus(ScheduledExamStatus.SCHEDULED);
        scheduleExam.setStartingAt(scheduleExamRequestDTO.getStartingAt());
        return scheduleExam;
    }

    public static ScheduleExamResponseDTO toResponseDTO(ScheduleExam scheduleExam) {
        ScheduleExamResponseDTO scheduleExamResponseDTO = new ScheduleExamResponseDTO();
        scheduleExamResponseDTO.setId(scheduleExam.getId());
        scheduleExamResponseDTO.setName(scheduleExam.getName());
        scheduleExamResponseDTO.setExamId(scheduleExam.getExam().getId());
        scheduleExamResponseDTO.setCollegeId(scheduleExam.getCollege() !=null ? scheduleExam.getCollege().getId() : null);
        scheduleExamResponseDTO.setStatus(scheduleExam.getStatus());
        scheduleExamResponseDTO.setStartingAt(scheduleExam.getStartingAt().toString());
        scheduleExamResponseDTO.setCreatedAt(scheduleExam.getCreatedAt().toString());
        scheduleExamResponseDTO.setUpdatedAt(scheduleExam.getUpdatedAt().toString());
        return scheduleExamResponseDTO;
    }

    public static List<ScheduleExamResponseDTO> toResponseDTO(List<ScheduleExam> scheduleExams) {
        return scheduleExams.stream().map(ScheduleExamMapper::toResponseDTO).toList();
    }

    public static ScheduleExam updateEntity(ScheduleExam scheduleExam, ScheduleExamRequestDTO dto) {
        if (dto.getName() != null)
            scheduleExam.setName(dto.getName());
        if (dto.getStartingAt() != null)
            scheduleExam.setStartingAt(dto.getStartingAt());
        if (dto.getStatus() != null)
            scheduleExam.setStatus(dto.getStatus());
        return scheduleExam;
    }
}
