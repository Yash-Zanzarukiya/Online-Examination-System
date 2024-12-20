package com.yashpz.examination_system.examination_system.messaging.consumer;

import com.yashpz.examination_system.examination_system.messaging.RabbitMQConsumer;
import com.yashpz.examination_system.examination_system.messaging.dto.MarksCalculationDTO;
import com.yashpz.examination_system.examination_system.service.ExamEvolutionService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarksCalculationConsumer implements RabbitMQConsumer<MarksCalculationDTO> {

    private final ExamEvolutionService examEvolutionService;

    @RabbitListener(queues = "${rabbitmq.queues.marks}")
    @Override
    public void handleMessage(MarksCalculationDTO marksCalculationDTO) {
        System.out.println("Received message: " + marksCalculationDTO.toString());
        examEvolutionService.calculateAndSaveMarks(marksCalculationDTO.getExamAttemptId());
    }
}
