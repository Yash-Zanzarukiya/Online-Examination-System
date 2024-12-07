package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.Question.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.McqOptionMapper;
import com.yashpz.examination_system.examination_system.model.McqOption;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.McqOptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class McqOptionService {

    private final McqOptionRepository mcqOptionRepository;
    private final QuestionService questionService;
    private final McqOptionMapper mcqOptionMapper;
    private final CloudinaryService cloudinaryService;

    public McqOptionService(McqOptionRepository mcqOptionRepository, McqOptionMapper mcqOptionMapper, CloudinaryService cloudinaryService, QuestionService questionService) {
        this.mcqOptionRepository = mcqOptionRepository;
        this.mcqOptionMapper = mcqOptionMapper;
        this.cloudinaryService = cloudinaryService;
        this.questionService = questionService;
    }

    @Transactional
    public McqOptionResponseDTO createMcqOption(McqOptionRequestDTO mcqOptionRequestDTO){
        McqOption option = mcqOptionMapper.toEntity(mcqOptionRequestDTO);

        Question question = questionService.getQuestionEntityById(mcqOptionRequestDTO.getQuestionId());

        option.setQuestion(question);

        if (mcqOptionRequestDTO.getImageFile() != null) {
            String imageUrl = cloudinaryService.uploadImage(mcqOptionRequestDTO.getImageFile());
            option.setImage(imageUrl);
        }

        mcqOptionRepository.save(option);

        if (mcqOptionRequestDTO.getIsCorrect())
            questionService.updateCorrectAnswer(question.getId(), option.getId());

        return mcqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public List<McqOptionResponseDTO> createMultipleMcqOptions(List<McqOptionRequestDTO> mcqOptionRequestDTOList) {
        return mcqOptionRequestDTOList.stream()
                .map(this::createMcqOption)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<McqOptionResponseDTO> createMultipleOptions(UUID questionId, List<McqOptionRequestDTO> options) {
        return options.stream()
                .map(option -> {
                    option.setQuestionId(questionId);
                    return createMcqOption(option);
                })
                .collect(Collectors.toList());
    }

    public List<McqOptionResponseDTO> getOptionsByQuestionId(UUID questionId) {
        return mcqOptionRepository.findAllByQuestionId(questionId)
                .stream()
                .map(mcqOptionMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public McqOptionResponseDTO getOptionById(UUID optionId) {
        McqOption option = mcqOptionRepository.findById(optionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid option ID"));
        return mcqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public McqOptionResponseDTO updateOption(UUID optionId, McqOptionRequestDTO mcqOptionRequestDTO) {
        McqOption option = mcqOptionRepository.findById(optionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid option ID"));

        option = mcqOptionMapper.updateEntity(option, mcqOptionRequestDTO);

        if (mcqOptionRequestDTO.getImageFile() != null) {
            if (option.getImage() != null)
                cloudinaryService.deleteImageByURL(option.getImage());
            String imageUrl = cloudinaryService.uploadImage(mcqOptionRequestDTO.getImageFile());
            option.setImage(imageUrl);
        }

        if (mcqOptionRequestDTO.getIsCorrect())
            questionService.updateCorrectAnswer(option.getQuestion().getId(), option.getId());

        mcqOptionRepository.save(option);

        return mcqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public List<McqOptionResponseDTO> updateMultipleOptions(List<McqOptionRequestDTO> mcqOptionRequestDTOList) {
        return mcqOptionRequestDTOList.stream()
                .map(mcqOptionRequestDTO -> updateOption(mcqOptionRequestDTO.getId(), mcqOptionRequestDTO))
                .collect(Collectors.toList());
    }

    public void deleteOption(UUID optionId) {
        McqOption option = mcqOptionRepository.findById(optionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid option ID"));

        if (option.getImage() != null)
            cloudinaryService.deleteImageByURL(option.getImage());

        mcqOptionRepository.delete(option);
    }

    @Transactional
    public void deleteOptionsByQuestionId(UUID questionId) {
        List<McqOption> options = mcqOptionRepository.findAllByQuestionId(questionId);
        options.forEach(option -> {
            if (option.getImage() != null)
                cloudinaryService.deleteImageByURL(option.getImage());
        });
        mcqOptionRepository.deleteAllByQuestionId(questionId);
    }

    public void deleteOptionImage(UUID optionId) {
        McqOption option = mcqOptionRepository.findById(optionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid option ID"));

        if (option.getImage() != null)
            cloudinaryService.deleteImageByURL(option.getImage());

        option.setImage(null);
        mcqOptionRepository.save(option);
    }
}
