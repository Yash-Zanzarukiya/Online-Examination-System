package com.yashpz.examination_system.examination_system.socket.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yashpz.examination_system.examination_system.socket.dto.MessageType;
import com.yashpz.examination_system.examination_system.socket.dto.SocketMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
@RequiredArgsConstructor
public class WebSocketMessageSender {

    private final ObjectMapper objectMapper;

    public <T> void sendMessage(WebSocketSession session, MessageType type, Enum<?> subtype, T payload) {
        if (session != null && session.isOpen()) {
            try {
                SocketMessageDTO message = new SocketMessageDTO(type, subtype.toString(), payload);
                String jsonMessage = objectMapper.writeValueAsString(message);
                session.sendMessage(new TextMessage(jsonMessage));
                System.out.println("Message sent: " + jsonMessage);
            } catch (JsonProcessingException e) {
                System.err.println("Error serializing message: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        } else {
            System.err.println("Cannot send message. Session is null or closed.");
        }
    }

}
