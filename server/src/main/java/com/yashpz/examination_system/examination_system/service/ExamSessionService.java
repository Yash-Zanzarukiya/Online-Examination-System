package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.constants.ExamSessionStatus;
import com.yashpz.examination_system.examination_system.constants.ExamSessionType;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.ExamSession;
import com.yashpz.examination_system.examination_system.repository.ExamAttemptRepository;
import com.yashpz.examination_system.examination_system.repository.ExamSessionRepository;
import com.yashpz.examination_system.examination_system.utils.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamSessionService {

    @Value("${EXAM_SESSION_TIMEOUT}")
    private Integer EXAM_SESSION_TIMEOUT_MINS;

    @Value("${EXAM_SESSION_MAX_DISCONNECTION}")
    private Integer EXAM_SESSION_MAX_DISCONNECTION;

    private final ExamSessionRepository examSessionRepository;
    private final ExamAttemptRepository examAttemptRepository;
    private final ExamActivityService examActivityService;
    private final JwtUtil jwtUtil;

    public ExamSession validateSessionToken(String sessionToken) {
        Boolean isValidToken = jwtUtil.validateToken(sessionToken);
        if (!isValidToken)
            throw new ApiError(HttpStatus.UNAUTHORIZED, "Invalid Session Token or Expired");

        Map<String, Object> payloadData = jwtUtil.getPayloadData(sessionToken);
        UUID sessionId = UUID.fromString((String) payloadData.get("sessionId"));

        return fetchExamSessionById(sessionId);
    }

    @Transactional
    public ExamSession createSession(UUID examAttemptId, UUID userId, UUID scheduledExamId, Integer examTime) {
        boolean isExists = examSessionRepository.existsByUserIdAndScheduledExamId(userId, scheduledExamId);
        if (isExists)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Session already present...");

        ExamSession session = new ExamSession();
        session.setUserId(userId);
        session.setScheduledExamId(scheduledExamId);
        session.setExamAttemptId(examAttemptId);
        session.setStatus(ExamSessionStatus.NOT_STARTED);
        session.setRemainingTime(examTime);
        session.setDisconnectCount(0);
        examSessionRepository.save(session);

        Map<String, Object> payloadData = Map.of(
                "sessionId", session.getId().toString(),
                "examAttemptId", examAttemptId.toString(),
                "userId", userId.toString(),
                "scheduledExamId", scheduledExamId.toString(),
                "createdAt", LocalDateTime.now().toString());

        String token = jwtUtil.generateToken("session", payloadData);

        session.setSessionToken(token);

        return examSessionRepository.save(session);
    }

    public void handleSessionConnection(){
        // implement if socket handler afterConnectionEstablished method business logic can be simplified
    }

    @Transactional
    public void handleSessionStart(UUID sessionId) {
        ExamSession session = fetchExamSessionById(sessionId);
        session.setStatus(ExamSessionStatus.CONNECTED);
        examSessionRepository.save(session);
    }

    @Transactional
    public synchronized boolean handleResumingSession(UUID sessionId) {
        ExamSession session = fetchExamSessionById(sessionId);
        UUID examAttemptId = session.getExamAttemptId();
        ExamAttemptStatus examAttemptStatus = examAttemptRepository.getStatusById(examAttemptId);

        if (session.getStatus() == ExamSessionStatus.COMPLETED || session.getStatus() == ExamSessionStatus.TERMINATED) {
            return false;
        }
        else if (session.getStatus() == ExamSessionStatus.DISCONNECTED && examAttemptStatus != ExamAttemptStatus.NOT_STARTED) {
            Boolean isTimeOut = LocalDateTime.now().minusMinutes(EXAM_SESSION_TIMEOUT_MINS)
                    .isAfter(session.getLastDisconnect());
            Boolean isMaxDisconnect = session.getDisconnectCount() >= EXAM_SESSION_MAX_DISCONNECTION;

            if (isMaxDisconnect || isTimeOut) {
                session.setStatus(ExamSessionStatus.TERMINATED);
                examAttemptRepository.updateStatus(examAttemptId, ExamAttemptStatus.TERMINATED);
                examSessionRepository.save(session);
                String reason = isMaxDisconnect ? "Maximum disconnection limit reached" : "Session timeout";
                examActivityService.handleExamEnd(examAttemptId, "Exam terminated due to " + reason);
                return false;
            }
        }

        session.setStatus(ExamSessionStatus.CONNECTED);
        examSessionRepository.save(session);

        if (examAttemptStatus != ExamAttemptStatus.NOT_STARTED)
            examAttemptRepository.updateStatus(examAttemptId, ExamAttemptStatus.IN_PROGRESS);

        return true;
    }

    @Transactional
    public void handleSessionPing(UUID sessionId, Integer remainingTime) {
        ExamSession session = fetchExamSessionById(sessionId);
        session.setRemainingTime(remainingTime);
        examSessionRepository.save(session);
    }

    @Transactional
    public synchronized void handleSessionDisconnect(String sessionToken) {
        ExamSession session = examSessionRepository.findBySessionToken(sessionToken);
        ExamAttempt examAttempt = fetchExamAttemptById(session.getExamAttemptId());

        if (session.getStatus() != ExamSessionStatus.CONNECTED) return;

        examActivityService.handleDisconnect(examAttempt.getId());

        session.setStatus(ExamSessionStatus.DISCONNECTED);

        if (examAttempt.getStatus()!=ExamAttemptStatus.NOT_STARTED) {
            session.setDisconnectCount(session.getDisconnectCount() + 1);
            session.setLastDisconnect(LocalDateTime.now());
        }

        examSessionRepository.save(session);

        if (examAttempt.getStatus() == ExamAttemptStatus.IN_PROGRESS)
            examAttempt.setStatus(ExamAttemptStatus.INTERRUPTED);

        examAttemptRepository.save(examAttempt);
    }

    @Transactional
    public void handleSubmitExam(UUID examAttemptId) {
        ExamSession session = examSessionRepository.findByExamAttemptId(examAttemptId);
        session.setStatus(ExamSessionStatus.COMPLETED);
        examSessionRepository.save(session);
    }

    public ExamSessionType getSessionType(UUID examAttemptId) {
        ExamAttemptStatus status = examAttemptRepository.getStatusById(examAttemptId);
        return status == ExamAttemptStatus.NOT_STARTED ? ExamSessionType.NORMAL : ExamSessionType.RESUMED;
    }

    // <--------------- Helpers --------------->

    public ExamSession fetchExamSessionById(UUID sessionId) {
        return examSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiError(HttpStatus.UNAUTHORIZED, "Session not found"));
    }

    public ExamAttempt fetchExamAttemptById(UUID examAttemptId) {
        return examAttemptRepository.findById(examAttemptId)
                .orElseThrow(() -> new ApiError(HttpStatus.UNAUTHORIZED, "Exam Attempt not found"));
    }

    public String getSessionTokenFromExamAttemptId(UUID examAttemptId) {
        return examSessionRepository.getSessionTokenFromExamAttemptId(examAttemptId);
    }
}
