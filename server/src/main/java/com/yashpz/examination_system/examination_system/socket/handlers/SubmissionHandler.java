package com.yashpz.examination_system.examination_system.socket.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.McqSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.ProgrammingSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.QuestionTimeSpentDTO;
import com.yashpz.examination_system.examination_system.service.ExamActivityService;
import com.yashpz.examination_system.examination_system.service.ExamResponseService;
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
public class SubmissionHandler {
    private final WebSocketMessageSender webSocketMessageSender;
    private final WebSocketSessionUtil webSocketSessionUtil;
    private final ExamResponseService examResponseService;
    private final ExamActivityService examActivityService;
    private final ObjectMapper objectMapper;

    private final Map<Submission, BiConsumer<WebSocketSession, SocketMessageDTO>> submissionHandlers = new EnumMap<>(Submission.class);

    @PostConstruct
    public void init() {
        submissionHandlers.put(Submission.MULTIPLE_CHOICE_SUBMISSION, this::handleMultipleChoiceSubmission);
        submissionHandlers.put(Submission.PROGRAMMING_SUBMISSION, this::handleProgrammingSubmission);
        submissionHandlers.put(Submission.UPDATE_TIME_SPENT, this::handleTimeSpentOnQuestion);
    }

    public void handleSubmission(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            Submission subtype = Submission.valueOf(socketMessageDTO.getSubtype());
            submissionHandlers.getOrDefault(subtype, this::handleUnknownSubmission).accept(session, socketMessageDTO);
        } catch (IllegalArgumentException e) {
            handleUnknownSubmission(session, socketMessageDTO);
        }
    }

    private void handleUnknownSubmission(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        sendSubmissionResponse(session, Submission.UNKNOWN_SUBMISSION, socketMessageDTO);
        System.out.println("Unknown submission");
    }

    private void handleMultipleChoiceSubmission(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            McqSubmissionRequestDTO mcqSubmissionRequestDTO = objectMapper.convertValue(socketMessageDTO.getPayload(), McqSubmissionRequestDTO.class);
            UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
            examResponseService.saveMcqResponse(mcqSubmissionRequestDTO, examAttemptId);
            examActivityService.handleAnswerSubmission(examAttemptId, mcqSubmissionRequestDTO.getQuestionNumber());
            sendSubmissionResponse(session, Submission.MULTIPLE_CHOICE_SUBMISSION_RES, "Multiple choice submission saved");
        } catch (Exception e) {
            sendSubmissionResponse(session, Submission.ERROR_PROCESSING_REQUEST, e.getMessage());
            System.err.println("Error processing Programming submission: " + e.getMessage());
        }
    }

    private void handleProgrammingSubmission(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            Object payload = socketMessageDTO.getPayload();
            ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO = objectMapper.convertValue(payload, ProgrammingSubmissionRequestDTO.class);
            UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
            examResponseService.saveProgrammingResponse(programmingSubmissionRequestDTO, examAttemptId);
            sendSubmissionResponse(session, Submission.PROGRAMMING_SUBMISSION_RES, "Programming submission saved");
        }  catch (Exception e) {
            sendSubmissionResponse(session, Submission.ERROR_PROCESSING_REQUEST, e.getMessage());
            System.err.println("Error processing Programming submission: " + e.getMessage());
        }
    }

    private void handleTimeSpentOnQuestion(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        try {
            Object payload = socketMessageDTO.getPayload();
            QuestionTimeSpentDTO questionTimeSpentDTO = objectMapper.convertValue(payload, QuestionTimeSpentDTO.class);
            UUID examAttemptId = webSocketSessionUtil.getExamAttemptId(session);
            examResponseService.updateTimeSpentOnQuestion(questionTimeSpentDTO.getQuestionId(), questionTimeSpentDTO.getTimeSpent(), examAttemptId);
        } catch (Exception e) {
            sendSubmissionResponse(session, Submission.ERROR_PROCESSING_REQUEST, e.getMessage());
            System.err.println("Error processing Question Time Spent: " + e.getMessage());
        }
    }

    // <--------------- Helpers --------------->

    private <T> void sendSubmissionResponse(WebSocketSession session, Submission submissionType, T payload) {
        webSocketMessageSender.sendMessage(session, MessageType.SUBMISSION, submissionType, payload);
    }
}
