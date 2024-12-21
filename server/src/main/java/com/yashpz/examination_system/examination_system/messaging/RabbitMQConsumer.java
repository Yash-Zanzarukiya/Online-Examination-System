package com.yashpz.examination_system.examination_system.messaging;

public interface RabbitMQConsumer<T> {
    void handleMessage(T message);
}
