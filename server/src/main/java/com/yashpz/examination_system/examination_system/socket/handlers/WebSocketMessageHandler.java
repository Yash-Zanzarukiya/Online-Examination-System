package com.yashpz.examination_system.examination_system.socket.handlers;

import com.yashpz.examination_system.examination_system.socket.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class WebSocketMessageHandler {
    private final ActionHandler actionHandler;
    private final ExamEventHandler examEventHandler;
    private final SubmissionHandler submissionHandler;

    public void handleMessage(WebSocketSession session, SocketMessageDTO socketMessageDTO) {
        MessageType messageType = socketMessageDTO.getType();
        switch (messageType) {
            case EXAM_EVENT:
                examEventHandler.handleExamEvent(session, socketMessageDTO);
                break;
            case ACTION:
                actionHandler.handleAction(session, socketMessageDTO);
                break;
            case SUBMISSION:
                submissionHandler.handleSubmission(session, socketMessageDTO);
                break;
            default:
                System.out.println("Unknown message type");
                break;
        }
    }
}
