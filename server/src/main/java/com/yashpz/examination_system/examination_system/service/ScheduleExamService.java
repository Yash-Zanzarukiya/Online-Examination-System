package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ScheduleExamMapper;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import com.yashpz.examination_system.examination_system.repository.ScheduleExamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ScheduleExamService {

    private final ScheduleExamRepository scheduleExamRepository;
    private final ExamService examService;
    private final CollegeService collegeService;

    @Transactional
    public ScheduleExamResponseDTO scheduleExam(ScheduleExamRequestDTO dto) {
        Exam exam = examService.fetchExamById(dto.getExamId());

        College college = dto.getCollegeId() != null
                ? collegeService.fetchCollegeById(dto.getCollegeId())
                : null;

        ScheduleExam scheduleExam = ScheduleExamMapper.toEntity(dto, exam, college);

        scheduleExamRepository.save(scheduleExam);

        return ScheduleExamMapper.toResponseDTO(scheduleExam);
    }

    public List<ScheduleExamResponseDTO> getExamSchedules(UUID examId, UUID collegeId, ScheduledExamStatus status) {
        List<ScheduleExam> schedules = scheduleExamRepository.findSchedulesByFilters(examId, collegeId, status);
        return ScheduleExamMapper.toResponseDTO(schedules);
    }

    public ScheduleExamResponseDTO getExamScheduleById(UUID id) {
        ScheduleExam scheduleExam = fetchScheduleExamById(id);
        return ScheduleExamMapper.toResponseDTO(scheduleExam);
    }

    @Transactional
    public ScheduleExamResponseDTO updateExamSchedule(UUID id, ScheduleExamRequestDTO dto) {
        ScheduleExam scheduleExam = fetchScheduleExamById(id);

        ScheduleExamMapper.updateEntity(scheduleExam, dto);

        scheduleExamRepository.save(scheduleExam);

        return ScheduleExamMapper.toResponseDTO(scheduleExam);
    }

    @Transactional
    public void updateScheduleExamStatus(UUID scheduledExamId, ScheduledExamStatus status){
        Integer i = scheduleExamRepository.updateScheduleExamStatus(scheduledExamId, status);
        if (i == 0)
            throw new ApiError(HttpStatus.NOT_FOUND, "Schedule exam not found");
    }

    @Transactional
    public void removeScheduleExam(UUID id) {
        if (!scheduleExamRepository.existsById(id))
            throw new ApiError(HttpStatus.NOT_FOUND, "Schedule exam not found");

        scheduleExamRepository.deleteById(id);
    }

    // <----------- Helpers ----------->

    public ScheduleExam fetchScheduleExamById(UUID id) {
        return scheduleExamRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Schedule exam not found"));
    }

    public UUID getExamIdByScheduledExamId(UUID scheduledExamId) {
        return scheduleExamRepository.getExamIdByScheduledExamId(scheduledExamId);
    }
}
