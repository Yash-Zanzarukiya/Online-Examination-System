package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;

import java.util.List;

public class ScheduleExamMapper {
    public static ScheduleExam toEntity(ScheduleExamRequestDTO scheduleExamRequestDTO, Exam exam, College college) {
        ScheduleExam scheduleExam = new ScheduleExam();
        scheduleExam.setExam(exam);
        scheduleExam.setCollege(college);
        scheduleExam.setStartingAt(scheduleExamRequestDTO.getStartingAt());
        return scheduleExam;
    }

    public static ScheduleExamResponseDTO toResponseDTO(ScheduleExam scheduleExam) {
        ScheduleExamResponseDTO scheduleExamResponseDTO = new ScheduleExamResponseDTO();
        scheduleExamResponseDTO.setId(scheduleExam.getId());
        scheduleExamResponseDTO.setExamId(scheduleExam.getExam().getId());
        scheduleExamResponseDTO.setCollegeId(scheduleExam.getCollege().getId());
        scheduleExamResponseDTO.setStartingAt(scheduleExam.getStartingAt().toString());
        scheduleExamResponseDTO.setCreatedAt(scheduleExam.getCreatedAt().toString());
        scheduleExamResponseDTO.setUpdatedAt(scheduleExam.getUpdatedAt().toString());
        return scheduleExamResponseDTO;
    }

    public static List<ScheduleExamResponseDTO> toResponseDTO(List<ScheduleExam> scheduleExams) {
        return scheduleExams.stream().map(ScheduleExamMapper::toResponseDTO).toList();
    }
}
