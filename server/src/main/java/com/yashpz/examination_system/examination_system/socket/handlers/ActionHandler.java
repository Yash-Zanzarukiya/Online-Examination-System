package com.yashpz.examination_system.examination_system.socket.handlers;

import com.yashpz.examination_system.examination_system.constants.ExamSessionType;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState.ActiveExamStateDTO;
import com.yashpz.examination_system.examination_system.service.ActiveExamService;
import com.yashpz.examination_system.examination_system.service.ExamActivityService;
import com.yashpz.examination_system.examination_system.service.ExamAttemptService;
import com.yashpz.examination_system.examination_system.service.ExamSessionService;
import com.yashpz.examination_system.examination_system.socket.dto.Action;
import com.yashpz.examination_system.examination_system.socket.dto.MessageType;
import com.yashpz.examination_system.examination_system.socket.dto.SocketMessageDTO;
import com.yashpz.examination_system.examination_system.socket.utils.WebSocketSessionUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class ActionHandler {
    private final WebSocketMessageSender webSocketMessageSender;
    private final WebSocketSessionUtil webSocketSessionUtil;
    private final ExamAttemptService examAttemptService;
    private final ExamSessionService examSessionService;
    private final ActiveExamService activeExamService;
    private final ExamActivityService examActivityService;

    private final Map<Action, BiConsumer<WebSocketSession, SocketMessageDTO>> actionHandlers = new EnumMap<>(Action.class);

    @PostConstruct
    public void init() {
        actionHandlers.put(Action.START_EXAM_REQ, this::handleStartExam);
        actionHandlers.put(Action.EXAM_QUESTION_REQ, this::handleExamQuestionRequest);
        actionHandlers.put(Action.SUBMIT_EXAM_REQ, this::handleSubmitExam);
        actionHandlers.put(Action.PING, this::handleSessionPing);
    }

    public void handleAction(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            Action subtype = Action.valueOf(socketMessageDTO.getSubtype());
            actionHandlers.getOrDefault(subtype, this::handleUnknownAction).accept(session, socketMessageDTO);
        } catch (IllegalArgumentException e) {
            handleUnknownAction(session, socketMessageDTO);
        }
    }

    private void handleUnknownAction(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        sendActionResponse(session, Action.UNKNOWN_ACTION, socketMessageDTO);
        System.out.println("Unknown action");
    }

    private void handleSessionPing(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        Object payload = socketMessageDTO.getPayload();
        if (payload instanceof Integer remainingTime) {
            UUID sessionId = webSocketSessionUtil.getSessionId(session);
            examSessionService.handleSessionPing(sessionId,remainingTime);
        }
        sendActionResponse(session, Action.PONG, "Ping Received");
        System.out.println("Ping action");
    }

    private void handleStartExam(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examAttemptService.startExam(examAttemptId, LocalDateTime.now());
        examActivityService.handleStartExam(examAttemptId);
        sendActionResponse(session, Action.START_EXAM_RES, "All the best");
        System.out.println("Start exam action");
    }

    private void handleExamQuestionRequest(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        Object payload = socketMessageDTO.getPayload();
        try {
            ExamSessionType examSessionType = ExamSessionType.valueOf((String) payload);
            UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
            ActiveExamStateDTO activeExamState = activeExamService.getActiveExamState(examAttemptId, examSessionType);
            sendActionResponse(session, Action.EXAM_QUESTION_RES, activeExamState);
            System.out.println("Exam Questions request with type: " + examSessionType);
        } catch (Exception e) {
            sendActionResponse(session, Action.ERROR_PROCESSING_REQUEST, "Invalid Session Type");
            System.out.println("Invalid ExamQuestions request action: " + payload);
        }
    }

    private void handleSubmitExam(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examAttemptService.submitExam(examAttemptId, LocalDateTime.now());
        examActivityService.handleSubmitExam(examAttemptId);
        sendActionResponse(session, Action.SUBMIT_EXAM_RES,"Exam Submitted successfully");
        System.out.println("Submit exam action");
    }

    // <--------------- Helpers --------------->

    private <T> void sendActionResponse(WebSocketSession session, Action actionType, T payload){
        webSocketMessageSender.sendMessage(session, MessageType.ACTION, actionType, payload);
    }
}
