package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Question.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.model.McqOption;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McqOptionMapper {

    public McqOption toEntity(McqOptionRequestDTO dto) {
        McqOption option = new McqOption();
        option.setOptionText(dto.getOptionText());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }

    public List<McqOption> toEntity(List<McqOptionRequestDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    public McqOptionResponseDTO toResponseDTO(McqOption option) {
        return new McqOptionResponseDTO(
                option.getId(),
                option.getQuestion().getId(),
                option.getOptionText(),
                option.getImage(),
                true
//                option.getQuestion().getCorrectAnswer() != null && option.getQuestion().getCorrectAnswer().getId().toString().equals(option.getId().toString())
        );
    }

    public List<McqOptionResponseDTO> toResponseDTO(List<McqOption> options) {
        return options.stream().map(this::toResponseDTO).toList();
    }

    public McqOption updateEntity(McqOption option, McqOptionRequestDTO dto) {
        if (dto.getOptionText()!=null) option.setOptionText(dto.getOptionText());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }
}
