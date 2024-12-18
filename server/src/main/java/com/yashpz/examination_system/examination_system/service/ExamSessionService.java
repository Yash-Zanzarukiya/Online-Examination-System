package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.contexts.ExamSessionContext;
import com.yashpz.examination_system.examination_system.constants.ExamAttemptStatus;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.ExamSession;
import com.yashpz.examination_system.examination_system.repository.ExamSessionRepository;
import com.yashpz.examination_system.examination_system.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ExamSessionService {
    private final ExamSessionRepository examSessionRepository;
    private final JwtUtil jwtUtil;

    @Value("${EXAM_SESSION.PING_THRESHOLD_MINUTES}")
    private int pingThresholdMinutes;

    public ExamSessionService(ExamSessionRepository examSessionRepository, JwtUtil jwtUtil) {
        this.examSessionRepository = examSessionRepository;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    public ExamSession createSession(ExamAttempt examAttempt, String visitorId) {
        ExamSession session = new ExamSession();
        session.setExamAttempt(examAttempt);
        session.setVisitorId(visitorId);
        session.setDisconnectCount(0);
        session.setIsDisconnected(false);
        session.setLastPing(LocalDateTime.now());

        long remainingTime = examAttempt.getExam().getExam().getTimeLimit() * 60;
        session.setRemainingTime(remainingTime);

        examSessionRepository.save(session);

        Map<String, Object> payloadData = Map.of(
                "sessionId", session.getId().toString(),
                "visitorId", visitorId,
                "examAttemptId", examAttempt.getId().toString()
        );
        String token = jwtUtil.generateToken("session", payloadData);
        session.setSessionToken(token);

        return examSessionRepository.save(session);
    }

    @Transactional
    public void handleSessionPing(Long remainingTime) {
        ExamSession session = getCurrentExamSession();
        session.setLastPing(LocalDateTime.now());
        session.setRemainingTime(remainingTime);
        examSessionRepository.save(session);
    }

    // TODO : Fetch and Return Saved Exam State
    @Transactional
    public void handleResumingSession() {
        ExamSession session = fetchExamSessionById(getCurrentExamSession().getId());

        if (session.getRemainingTime() <= 0 )
            throw new ApiError(HttpStatus.BAD_REQUEST, "Session Expired");

        if (session.getExamAttempt().getStatus() == ExamAttemptStatus.SUBMITTED)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Exam Attempt already completed");

        if (!session.getIsDisconnected() && session.getLastPing().isAfter(LocalDateTime.now().minusMinutes(pingThresholdMinutes)))
            throw new ApiError(HttpStatus.BAD_REQUEST, "Session is already active");

        session.setLastPing(LocalDateTime.now());
        session.setIsDisconnected(false);
        session.getExamAttempt().setStatus(ExamAttemptStatus.IN_PROGRESS);
        examSessionRepository.save(session);
    }

    @Transactional
    public void detectDisconnections() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(pingThresholdMinutes);
        System.out.println("Threshold : " + threshold);

        List<ExamSession> disconnectedSessions = examSessionRepository.findByLastPingBeforeAndExamAttemptStatus(threshold, ExamAttemptStatus.IN_PROGRESS);

        for (ExamSession session : disconnectedSessions) {
            session.setIsDisconnected(true);
            session.setDisconnectCount(session.getDisconnectCount() + 1);
            session.getExamAttempt().setStatus(ExamAttemptStatus.INTERRUPTED);
        }

        examSessionRepository.saveAll(disconnectedSessions);
        System.out.println("Disconnected Sessions : " + disconnectedSessions.size());
    }

    public ExamSession validateSessionToken(String sessionToken, String visitorId) {
        Boolean isValid = jwtUtil.validateToken(sessionToken);
        if (!isValid)
            throw new ApiError(HttpStatus.UNAUTHORIZED, "Invalid Session Token or Expired");

        Map<String, Object> payloadData = jwtUtil.getPayloadData(sessionToken);
        UUID sessionId = UUID.fromString((String) payloadData.get("sessionId"));

        ExamSession session = fetchExamSessionById(sessionId);

        if (!session.getSessionToken().equals(sessionToken)) {
            throw new ApiError(HttpStatus.UNAUTHORIZED, "Session Token Mismatch Detected");
        }

        if (!session.getVisitorId().equals(visitorId)) {
            throw new ApiError(HttpStatus.UNAUTHORIZED, "Device Mismatch Detected");
        }

        return session;
    }

    // <--------------- Helpers --------------->

    public ExamSession fetchExamSessionById(UUID sessionId) {
        return examSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ApiError(HttpStatus.UNAUTHORIZED,"Session not found"));
    }

    public ExamSession getCurrentExamSession() {
        ExamSession session = ExamSessionContext.getExamSession();
        if (session == null)
            throw new ApiError(HttpStatus.UNAUTHORIZED, "No valid session found.");
        return session;
    }
}
