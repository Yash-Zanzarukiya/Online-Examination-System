package com.yashpz.examination_system.examination_system.socket.handlers;

import com.yashpz.examination_system.examination_system.dto.ExamResponse.QuestionTimeSpentDTO;
import com.yashpz.examination_system.examination_system.service.ExamActivityService;
import com.yashpz.examination_system.examination_system.service.ExamAttemptService;
import com.yashpz.examination_system.examination_system.socket.dto.ExamEvent;
import com.yashpz.examination_system.examination_system.socket.dto.MessageType;
import com.yashpz.examination_system.examination_system.socket.dto.SocketMessageDTO;
import com.yashpz.examination_system.examination_system.socket.dto.Submission;
import com.yashpz.examination_system.examination_system.socket.utils.WebSocketSessionUtil;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.EnumMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class ExamEventHandler {
    private final WebSocketMessageSender webSocketMessageSender;
    private final ExamAttemptService examAttemptService;
    private final WebSocketSessionUtil webSocketSessionUtil;
    private final ExamActivityService examActivityService;

    private final Map<ExamEvent, BiConsumer<WebSocketSession, SocketMessageDTO>> eventHandlers = new EnumMap<>(ExamEvent.class);

    @PostConstruct
    public void init() {
        eventHandlers.put(ExamEvent.LOGOUT, this::handleLogoutEvent);
        eventHandlers.put(ExamEvent.TAB_SWITCH, this::handleTabSwitchEvent);
        eventHandlers.put(ExamEvent.QUESTION_NAVIGATION, this::handleQuestionNavigationEvent);
        eventHandlers.put(ExamEvent.COPY, this::handleCopyEvent);
        eventHandlers.put(ExamEvent.PASTE, this::handlePasteEvent);
        eventHandlers.put(ExamEvent.CLOSE_ATTEMPT, this::handleCloseAttemptEvent);
    }

    public void handleExamEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            ExamEvent subtype = ExamEvent.valueOf(socketMessageDTO.getSubtype());
            eventHandlers.getOrDefault(subtype, this::handleUnknownEvent).accept(session, socketMessageDTO);
        } catch (IllegalArgumentException e) {
            handleUnknownEvent(session, socketMessageDTO);
        }
    }

    private void handleUnknownEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        sendEventResponse(session, ExamEvent.UNKNOWN_EVENT, "Unknown Event detected");
        System.out.println("Unknown event");
    }

    private void handleLogoutEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        System.out.println("Logout event");
    }

    private void handleTabSwitchEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examActivityService.handleTabSwitch(examAttemptId);
        sendEventResponse(session, ExamEvent.TAB_SWITCH_RES, "Tab switch detected");
    }

    private void handleQuestionNavigationEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            String message = (String) socketMessageDTO.getPayload();
            UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
            examActivityService.handleQuestionNavigation(examAttemptId, message);
        } catch (Exception e) {
            System.err.println("Error processing question navigation message: " + e.getMessage());
        }
    }

    private void handleCopyEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examActivityService.handleCopy(examAttemptId);
        sendEventResponse(session, ExamEvent.COPY_RES, "Copy event detected");
    }

    private void handlePasteEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examActivityService.handlePaste(examAttemptId);
        sendEventResponse(session, ExamEvent.PASTE_RES, "Paste event detected");
    }

    private void handleCloseAttemptEvent(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
        examActivityService.handleCloseAttempt(examAttemptId);
        sendEventResponse(session, ExamEvent.CLOSE_ATTEMPT_RES, "Attempt to close browser or tab");
    }

    // <--------------- Helpers --------------->

    private <T> void sendEventResponse(WebSocketSession session, ExamEvent eventType, T payload) {
        webSocketMessageSender.sendMessage(session, MessageType.EXAM_EVENT, eventType, payload);
    }
}
