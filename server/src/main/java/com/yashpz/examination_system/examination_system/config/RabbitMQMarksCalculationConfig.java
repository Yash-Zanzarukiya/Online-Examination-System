package com.yashpz.examination_system.examination_system.config;

import com.yashpz.examination_system.examination_system.messaging.RabbitMQProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQMarksCalculationConfig {

    private final RabbitMQProperties rabbitMQProperties;

    @Bean
    public Queue marksQueue() {
        return new Queue(rabbitMQProperties.getMarksQueue(), true);
    }

    @Bean
    public DirectExchange marksExchange() {
        return new DirectExchange(rabbitMQProperties.getMarksExchange());
    }

    @Bean
    public Binding marksBinding(Queue marksQueue, DirectExchange marksExchange, RabbitMQProperties rabbitMQProperties) {
        return BindingBuilder.bind(marksQueue).to(marksExchange).with(rabbitMQProperties.getMarksRoutingKey());
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }
}
