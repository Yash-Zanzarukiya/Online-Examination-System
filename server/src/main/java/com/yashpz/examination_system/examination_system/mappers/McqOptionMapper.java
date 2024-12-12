package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.model.McqOption;

import java.util.List;

public class McqOptionMapper {

    public static McqOption toEntity(McqOptionRequestDTO dto) {
        McqOption option = new McqOption();
        option.setOptionText(dto.getOptionText());
        option.setIsCorrect(dto.getIsCorrect());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }

    public static List<McqOption> toEntity(List<McqOptionRequestDTO> dtos) {
        return dtos.stream().map(McqOptionMapper::toEntity).toList();
    }

    public static McqOptionResponseDTO toResponseDTO(McqOption option) {
        return new McqOptionResponseDTO(
                option.getId(),
                option.getQuestion().getId(),
                option.getOptionText(),
                option.getImage(),
                option.getIsCorrect()
        );
    }

    public static List<McqOptionResponseDTO> toResponseDTO(List<McqOption> options) {
        return options.stream().map(McqOptionMapper::toResponseDTO).toList();
    }

    public static McqOption updateEntity(McqOption option, McqOptionRequestDTO dto) {
        if (dto.getOptionText()!=null) option.setOptionText(dto.getOptionText());
        if (dto.getIsCorrect()!=null) option.setIsCorrect(dto.getIsCorrect());
        if (dto.getImageUrl()!=null) option.setImage(dto.getImageUrl());
        return option;
    }
}
