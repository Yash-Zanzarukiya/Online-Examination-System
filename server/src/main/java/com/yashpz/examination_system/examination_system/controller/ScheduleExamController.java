package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.ValidationGroups;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.service.ScheduleExamService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule-exam")
public class ScheduleExamController {

    private final ScheduleExamService scheduleExamService;

    public ScheduleExamController(ScheduleExamService scheduleExamService) {
        this.scheduleExamService = scheduleExamService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleExamResponseDTO>> scheduleExam(@RequestBody @Validated(ValidationGroups.Create.class)  ScheduleExamRequestDTO dto) {
        ScheduleExamResponseDTO scheduleExam = scheduleExamService.scheduleExam(dto);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, scheduleExam, "Exam scheduled successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleExamResponseDTO>> getExamScheduleById(@PathVariable UUID id) {
        ScheduleExamResponseDTO scheduleExam = scheduleExamService.getExamScheduleById(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, scheduleExam, "Exam schedule fetched successfully");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduleExamResponseDTO>>> getExamSchedules(
            @RequestParam(required = false) UUID examId,
            @RequestParam(required = false) UUID collegeId,
            @RequestParam(required = false, defaultValue = "false") Boolean upcoming
    ) {
        List<ScheduleExamResponseDTO> schedules = scheduleExamService.getExamSchedules(examId, collegeId, upcoming);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, schedules, "Exam schedules fetched successfully");
    }

    @PatchMapping("/{id}/reschedule")
    public ResponseEntity<ApiResponse<ScheduleExamResponseDTO>> rescheduleExam(
            @PathVariable UUID id,
            @RequestBody @Validated(ValidationGroups.Update.class) ScheduleExamRequestDTO dto
    ) {
        ScheduleExamResponseDTO rescheduledExam = scheduleExamService.rescheduleExam(id, dto);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, rescheduledExam, "Exam rescheduled successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeScheduleExam(@PathVariable UUID id) {
        scheduleExamService.removeScheduleExam(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam schedule removed successfully");
    }
}
