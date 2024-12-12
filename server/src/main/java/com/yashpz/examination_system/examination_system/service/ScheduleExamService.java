package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ScheduleExamMapper;
import com.yashpz.examination_system.examination_system.model.College;
import com.yashpz.examination_system.examination_system.model.Exam;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import com.yashpz.examination_system.examination_system.repository.ScheduleExamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ScheduleExamService {

    private final ScheduleExamRepository scheduleExamRepository;
    private final ExamService examService;
    private final CollegeService collegeService;

    public ScheduleExamService( ScheduleExamRepository scheduleExamRepository, ExamService examService, CollegeService collegeService) {
        this.scheduleExamRepository = scheduleExamRepository;
        this.examService = examService;
        this.collegeService = collegeService;
    }

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

    public List<ScheduleExamResponseDTO> getExamSchedules(UUID examId, UUID collegeId, Boolean upcoming) {
        List<ScheduleExam> schedules = scheduleExamRepository.findSchedulesByFilters(examId, collegeId, upcoming);
        return ScheduleExamMapper.toResponseDTO(schedules);
    }

    public ScheduleExamResponseDTO getExamScheduleById(UUID id) {
        ScheduleExam scheduleExam = fetchScheduleExamById(id);
        return ScheduleExamMapper.toResponseDTO(scheduleExam);
    }

    @Transactional
    public ScheduleExamResponseDTO rescheduleExam(UUID id, ScheduleExamRequestDTO dto) {
        ScheduleExam scheduleExam = fetchScheduleExamById(id);

        College college = dto.getCollegeId() != null
                ? collegeService.fetchCollegeById(dto.getCollegeId())
                : null;

        scheduleExam.setCollege(college);
        scheduleExam.setStartingAt(dto.getStartingAt());
        scheduleExamRepository.save(scheduleExam);

        return ScheduleExamMapper.toResponseDTO(scheduleExam);
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
}
