package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.Question.McqOptionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.model.McqOption;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class McqOptionMapper {

    public McqOption toEntity(McqOptionDTO dto) {
        McqOption option = new McqOption();
        option.setOptionText(dto.getOptionText());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }

    public List<McqOption> toEntity(List<McqOptionDTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    public McqOptionResponseDTO toResponseDTO(McqOption option) {
        return new McqOptionResponseDTO(
                option.getId(),
                option.getQuestion().getId(),
                option.getOptionText(),
                option.getImage(),
                option.getQuestion().getCorrectAnswer() != null && option.getQuestion().getCorrectAnswer().getId().equals(option.getId())
        );
    }

    public List<McqOptionResponseDTO> toResponseDTO(List<McqOption> options) {
        return options.stream().map(this::toResponseDTO).toList();
    }

    public McqOption updateEntity(McqOption option, McqOptionDTO dto) {
        if (dto.getOptionText()!=null) option.setOptionText(dto.getOptionText());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }
}
