package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.constants.ScheduledExamStatus;
import com.yashpz.examination_system.examination_system.constants.ValidationGroups;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ScheduleExam.ScheduleExamResponseDTO;
import com.yashpz.examination_system.examination_system.service.ScheduleExamService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/schedule-exam")
@RequiredArgsConstructor
public class ScheduleExamController {

    private final ScheduleExamService scheduleExamService;

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
            @RequestParam(required = false ) ScheduledExamStatus status
    ) {
        List<ScheduleExamResponseDTO> schedules = scheduleExamService.getExamSchedules(examId, collegeId, status);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, schedules, "Exam schedules fetched successfully");
    }

    @PatchMapping("/{id}/update")
    public ResponseEntity<ApiResponse<ScheduleExamResponseDTO>> updateExamSchedule(
            @PathVariable UUID id,
            @RequestBody @Validated(ValidationGroups.Update.class) ScheduleExamRequestDTO dto
    ) {
        ScheduleExamResponseDTO examSchedule = scheduleExamService.updateExamSchedule(id, dto);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, examSchedule, "Exam schedule updated successfully");
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ScheduleExamResponseDTO>> updateScheduleExamStatus(
            @PathVariable UUID id,
            @RequestParam ScheduledExamStatus status
    ) {
        ScheduleExamResponseDTO updatedScheduleExam = scheduleExamService.updateScheduleExamStatus(id, status);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedScheduleExam, "Exam schedule status updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> removeScheduleExam(@PathVariable UUID id) {
        scheduleExamService.removeScheduleExam(id);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Exam schedule removed successfully");
    }
}
