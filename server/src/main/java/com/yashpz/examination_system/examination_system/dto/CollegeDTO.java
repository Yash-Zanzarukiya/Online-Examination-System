package com.yashpz.examination_system.examination_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollegeDTO {
    private UUID id;
    private String name;
}

