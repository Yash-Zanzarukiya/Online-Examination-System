package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.contexts.ExamSessionContext;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Auth.LoginDTO;
import com.yashpz.examination_system.examination_system.dto.Auth.UserDataDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamAttemptMapper;
import com.yashpz.examination_system.examination_system.messaging.producer.MarksCalculationProducer;
import com.yashpz.examination_system.examination_system.model.*;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ScheduleExamService examScheduleService;
    private final ExamEvolutionService examEvolutionService;
    private final ExamSessionService examSessionService;
    private final ExamAuthService examAuthService;
    private final MarksCalculationProducer marksCalculationProducer;

    @Transactional
    public String createExamAttempt(ExamAttemptRequestDTO examAttemptRequestDTO) {
        LoginDTO loginDTO = new LoginDTO(examAttemptRequestDTO.getIdentifier(), examAttemptRequestDTO.getPassword());
        User user = examAuthService.authenticateUser(loginDTO);

        ExamAttempt existingExamAttempt = examAttemptRepository.findByUserIdAndExamId(user.getId(),
                examAttemptRequestDTO.getScheduledExamId());

        if (existingExamAttempt != null) {
            boolean notSubmitted = EnumSet
                    .of(ExamAttemptStatus.NOT_STARTED, ExamAttemptStatus.IN_PROGRESS, ExamAttemptStatus.INTERRUPTED)
                    .contains(existingExamAttempt.getStatus());

            if (notSubmitted) {
                return examSessionService
                        .getSessionTokenFromExamAttemptId(existingExamAttempt.getId());
            } else if (existingExamAttempt.getStatus() == ExamAttemptStatus.TERMINATED) {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Examination is Terminated...");
            } else {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Already Submitted...");
            }
        }

        ScheduleExam scheduledExam = examScheduleService
                .fetchScheduleExamById(examAttemptRequestDTO.getScheduledExamId());

        ExamAttempt examAttempt = ExamAttemptMapper.toEntity(scheduledExam, user);
        examAttemptRepository.save(examAttempt);

        ExamSession session = examSessionService.createSession(examAttempt.getId(), user.getId(), scheduledExam.getId(),
                scheduledExam.getExam().getTimeLimit());

        return session.getSessionToken();
    }

    public void startExam(UUID examAttemptId, LocalDateTime startTime) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        if (examAttempt.getStatus() == ExamAttemptStatus.NOT_STARTED) {
            examAttempt.setStatus(ExamAttemptStatus.IN_PROGRESS);
            examAttempt.setStartTime(startTime);
            examAttemptRepository.save(examAttempt);
        }
//        else {
//            throw new ApiError(HttpStatus.BAD_REQUEST, "Exam already started...");
//        }
    }

    @Transactional
    public void submitExam(UUID examAttemptId, LocalDateTime submissionTime) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        examAttempt.setStatus(ExamAttemptStatus.SUBMITTED);
        examAttempt.setEndTime(submissionTime);
        examAttemptRepository.save(examAttempt);

        examSessionService.handleSubmitExam(examAttemptId);

        marksCalculationProducer.publishMarksCalculationRequest(examAttempt.getId());
    }

    @Transactional
    public void updateExamAttemptStatus(UUID examAttemptId, ExamAttemptStatus status) {
        examAttemptRepository.updateStatus(examAttemptId, status);
    }

    public void updateProgrammingMarks(UUID programmingSubmissionId, int marks) {
        examEvolutionService.updateProgrammingMarks(programmingSubmissionId, marks);
    }

    public boolean isNormalAttempt(UUID examAttemptId) {
        ExamAttempt examAttempt = fetchExamAttemptById(examAttemptId);
        return examAttempt.getStatus() == ExamAttemptStatus.NOT_STARTED;
    }

    // <--------------- Helpers --------------->

    public ExamAttempt fetchExamAttemptById(UUID examAttemptId) {
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam Attempt not found"));
    }

    public UUID getExamIdFromExamAttemptId(UUID examAttemptId) {
        UUID id = examAttemptRepository.getExamIdFromExamAttemptId(examAttemptId);
        if (id == null)
            throw new ApiError(HttpStatus.NOT_FOUND, "Invalid Exam Attempt Id");
        return id;
    }

    public UUID getScheduledExamIdFromExamAttemptId(UUID examAttemptId) {
        UUID id = examAttemptRepository.getScheduledExamIdFromExamAttemptId(examAttemptId);
        if (id == null)
            throw new ApiError(HttpStatus.NOT_FOUND, "Invalid Exam Attempt Id");
        return id;
    }

    public ExamAttempt fetchCurrentExamAttempt() {
        return null;
    }
}
