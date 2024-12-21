package com.yashpz.examination_system.examination_system.messaging.producer;

import com.yashpz.examination_system.examination_system.messaging.RabbitMQProducer;
import com.yashpz.examination_system.examination_system.messaging.RabbitMQProperties;
import com.yashpz.examination_system.examination_system.messaging.dto.MarksCalculationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarksCalculationProducer {

    private final RabbitMQProducer producer;
    private final RabbitMQProperties properties;

    public void publishMarksCalculationRequest(UUID examAttemptId) {
        producer.sendMessage(
                properties.getMarksExchange(),
                properties.getMarksRoutingKey(),
                new MarksCalculationDTO(examAttemptId)
        );
    }
}