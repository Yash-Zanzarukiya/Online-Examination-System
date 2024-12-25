package com.yashpz.examination_system.examination_system.socket.utils;

import com.yashpz.examination_system.examination_system.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketSessionUtil {
    private final JwtUtil jwtUtil;

    public Map<String, Object>  getSessionTokenPayload(WebSocketSession session){
        String sessionToken = (String) session.getAttributes().get("sessionToken");
        return jwtUtil.getPayloadData(sessionToken);
    }

    public UUID getExamAttemptId(WebSocketSession session){
        Map<String, Object> payloadData = this.getSessionTokenPayload(session);
        return UUID.fromString((String) payloadData.get("examAttemptId"));
    }

    public UUID getSessionId(WebSocketSession session){
        Map<String, Object> payloadData = this.getSessionTokenPayload(session);
        return UUID.fromString((String) payloadData.get("sessionId"));
    }

    public UUID getUserId(WebSocketSession session){
        Map<String, Object> payloadData = this.getSessionTokenPayload(session);
        return UUID.fromString((String) payloadData.get("userId"));
    }

    public UUID getScheduledExamId(WebSocketSession session){
        Map<String, Object> payloadData = this.getSessionTokenPayload(session);
        return UUID.fromString((String) payloadData.get("scheduledExamId"));
    }
}
