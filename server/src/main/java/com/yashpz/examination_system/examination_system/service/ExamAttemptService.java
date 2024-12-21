package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.contexts.ExamSessionContext;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ExamAttemptRequestDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.ExamAttemptMapper;
import com.yashpz.examination_system.examination_system.messaging.producer.MarksCalculationProducer;
import com.yashpz.examination_system.examination_system.model.*;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamAttemptService {

    private final ExamAttemptRepository examAttemptRepository;
    private final ScheduleExamService examScheduleService;
    private final ExamEvolutionService examEvolutionService;
    private final ExamSessionService examSessionService;
    private final AuthService authService;
    private final MarksCalculationProducer marksCalculationProducer;

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

    public void updateExamAttemptStatus(UUID examAttemptId, ExamAttemptStatus status) {
        ExamAttempt examAttempt = examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Exam Attempt not found"));
        examAttempt.setStatus(status);
        examAttemptRepository.save(examAttempt);
    }

    @Transactional
    public void submitExam(ExamAttemptRequestDTO examAttemptRequestDTO) {
        ExamAttempt examAttempt = fetchCurrentExamAttempt();
        ExamAttemptMapper.updateEntity(examAttempt, examAttemptRequestDTO);
        examAttemptRepository.save(examAttempt);
        marksCalculationProducer.publishMarksCalculationRequest(examAttempt.getId());
    }

    // <--------------- Helpers --------------->

    public ExamAttempt fetchCurrentExamAttempt() {
        UUID examAttemptId = Objects.requireNonNull(ExamSessionContext.getExamSession()).getExamAttempt().getId();
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

    public void updateProgrammingMarks(UUID programmingSubmissionId, int marks) {
        examEvolutionService.updateProgrammingMarks(programmingSubmissionId, marks);
    }
}
