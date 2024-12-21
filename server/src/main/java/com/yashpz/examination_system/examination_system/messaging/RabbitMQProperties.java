package com.yashpz.examination_system.examination_system.messaging;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class RabbitMQProperties {

    @Value("${rabbitmq.queues.marks}")
    private String marksQueue;

    @Value("${rabbitmq.exchanges.marks}")
    private String marksExchange;

    @Value("${rabbitmq.routingKeys.marks}")
    private String marksRoutingKey;
}