package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.contexts.ExamSessionContext;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamAttemptMapper;
import com.yashpz.examination_system.examination_system.model.*;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ScheduleExamService examScheduleService;
    private final ExamSessionService examSessionService;
    private final AuthService authService;

    public ExamAttemptService(ExamAttemptRepository examAttemptRepository, ScheduleExamService examScheduleService, ExamSessionService examSessionService, AuthService authService) {
        this.examSessionService = examSessionService;
        this.examAttemptRepository = examAttemptRepository;
        this.examScheduleService = examScheduleService;
        this.authService = authService;
    }

    @Transactional
    public String createExamAttempt(ExamAttemptRequestDTO examAttemptRequestDTO) {
        User user = authService.fetchCurrentAuth().getUser();

        ExamAttempt existingExamAttempt = examAttemptRepository.findByUserIdAndExamId(user.getId(), examAttemptRequestDTO.getExamId());

        if (existingExamAttempt != null) {
            if (existingExamAttempt.getStatus() == ExamAttemptStatus.INTERRUPTED) {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Your previous attempt was interrupted. Please resume the exam", existingExamAttempt.getId());
            } else {
                throw new ApiError(HttpStatus.BAD_REQUEST, "Exam Attempt already exists");
            }
        }

        ScheduleExam exam = examScheduleService.fetchScheduleExamById(examAttemptRequestDTO.getExamId());

        ExamAttempt examAttempt = ExamAttemptMapper.toEntity(examAttemptRequestDTO, exam, user);
        examAttemptRepository.save(examAttempt);

        ExamSession session = examSessionService.createSession(examAttempt, examAttemptRequestDTO.getVisitorId());

        return session.getSessionToken();
    }

    @Transactional
    public void updateExamAttempt(ExamAttemptRequestDTO examAttemptRequestDTO) {
        ExamAttempt examAttempt = fetchCurrentExamAttempt();
        ExamAttemptMapper.updateEntity(examAttempt, examAttemptRequestDTO);
        examAttemptRepository.save(examAttempt);
    }

    @Transactional
    public void submitExam(ExamAttemptRequestDTO examAttemptRequestDTO) {
        ExamAttempt examAttempt = fetchCurrentExamAttempt();
        ExamAttemptMapper.updateEntity(examAttempt, examAttemptRequestDTO);
        examAttemptRepository.save(examAttempt);
    }

    // <--------------- Helpers --------------->

    public ExamAttempt fetchCurrentExamAttempt() {
        UUID examAttemptId = Objects.requireNonNull(ExamSessionContext.getExamSession()).getExamAttempt().getId();
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam Attempt not found"));
    }
}
