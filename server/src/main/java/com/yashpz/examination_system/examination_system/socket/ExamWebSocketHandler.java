package com.yashpz.examination_system.examination_system.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashpz.examination_system.examination_system.constants.ExamSessionType;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import com.yashpz.examination_system.examination_system.service.ScheduleExamService;
import com.yashpz.examination_system.examination_system.socket.dto.*;
import com.yashpz.examination_system.examination_system.socket.handlers.WebSocketMessageHandler;
import com.yashpz.examination_system.examination_system.socket.handlers.WebSocketMessageSender;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.yashpz.examination_system.examination_system.constants.ExamSessionStatus;
import com.yashpz.examination_system.examination_system.model.ExamSession;
import com.yashpz.examination_system.examination_system.service.ExamSessionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ExamWebSocketHandler extends TextWebSocketHandler {

    private final WebSocketSessionRegistry sessionRegistry;
    private final ExamSessionService examSessionService;
    private final ScheduleExamService scheduleExamService;
    private final WebSocketMessageHandler webSocketMessageHandler;
    private final WebSocketMessageSender webSocketMessageSender;
    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionToken = (String) session.getAttributes().get("sessionToken");

        ExamSession examSession = examSessionService.validateSessionToken(sessionToken);

        // If the session is completed or terminated, then the connection is rejected.
        if (examSession.getStatus() == ExamSessionStatus.COMPLETED
                || examSession.getStatus() == ExamSessionStatus.TERMINATED) {
            session.close(CloseStatus.NOT_ACCEPTABLE);
            System.out.println("Connection rejected (" + examSession.getStatus() + ") : " + sessionToken);
            return;
        }

        // If the session is disconnected, then the connection is resumed after validation.
        else if (examSession.getStatus() == ExamSessionStatus.DISCONNECTED) {
            // If the session resume request is not valid, then the connection is rejected.
            boolean isAccepted = examSessionService.handleResumingSession(examSession.getId());
            if (!isAccepted) {
                session.close(CloseStatus.NOT_ACCEPTABLE);
                System.out.println("Connection rejected (RESUME) : " + sessionToken);
                return;
            }

            // If the session resume request is valid,
            // then connection is accepted and previous session is terminated.
            sessionRegistry.forceLogoutToPreviousSession(sessionToken);
            sessionRegistry.addSession(sessionToken, session);
            System.out.println("Connection resumed: " + sessionToken);
        }

        // If the session is connected, then the connection is accepted and previous session is terminated.
        else if (examSession.getStatus() == ExamSessionStatus.CONNECTED) {
            sessionRegistry.forceLogoutToPreviousSession(sessionToken);
            sessionRegistry.addSession(sessionToken, session);
            System.out.println("New Connection after forced logouts: " + sessionToken);
        }

        // The session is never started, so the connection is accepted.
        else {
            examSessionService.handleSessionStart(examSession.getId());
            sessionRegistry.addSession(sessionToken, session);
            System.out.println("Connection established: " + sessionToken);
        }

        ExamSessionType sessionType = examSessionService.getSessionType(examSession.getExamAttemptId());
        ScheduleExam scheduleExam = scheduleExamService.fetchScheduleExamById(examSession.getScheduledExamId());
        ActiveExamData activeExamData = new ActiveExamData(scheduleExam.getStatus(), scheduleExam.getStartingAt());
        SocketConnectionDTO connectionDTO = new SocketConnectionDTO(sessionType, activeExamData, examSession.getRemainingTime(), "");

        webSocketMessageSender.sendMessage(session, MessageType.EXAM_EVENT, ExamEvent.CONNECT_RES, connectionDTO);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            System.out.println("Message received: " + message.getPayload());
            SocketMessageDTO socketMessage = objectMapper.readValue(message.getPayload(), SocketMessageDTO.class);
            webSocketMessageHandler.handleMessage(session, socketMessage);
            System.out.println("--------------------------------------------");
        } catch (JsonProcessingException e) {
            System.err.println("Invalid message structure: " + e.getMessage());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionToken = (String) session.getAttributes().get("sessionToken");
        examSessionService.handleSessionDisconnect(sessionToken);
        sessionRegistry.closeAndRemoveSession(sessionToken);
        System.out.println("Connection closed: " + sessionToken);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        String sessionToken = (String) session.getAttributes().get("sessionToken");
        examSessionService.handleSessionDisconnect(sessionToken);
        sessionRegistry.closeAndRemoveSession(sessionToken);
        System.err.println("Transport error: " + exception.getMessage());
    }
}