package com.yashpz.examination_system.examination_system.socket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocketMessageDTO {
    private MessageType type;
    private String subtype;
    private Object payload;
}