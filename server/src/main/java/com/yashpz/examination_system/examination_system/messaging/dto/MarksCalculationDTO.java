package com.yashpz.examination_system.examination_system.messaging.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksCalculationDTO{
    private UUID examAttemptId;
}
