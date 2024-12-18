package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.McqOptionMapper;
import com.yashpz.examination_system.examination_system.model.McqOption;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.McqOptionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class McqOptionService {

    private final McqOptionRepository mcqOptionRepository;
    private final QuestionService questionService;
    private final CloudinaryService cloudinaryService;

    public McqOptionService(McqOptionRepository mcqOptionRepository, CloudinaryService cloudinaryService, QuestionService questionService) {
        this.mcqOptionRepository = mcqOptionRepository;
        this.cloudinaryService = cloudinaryService;
        this.questionService = questionService;
    }

    @Transactional
    public McqOptionResponseDTO createMcqOption(McqOptionRequestDTO mcqOptionRequestDTO){
        McqOption option = McqOptionMapper.toEntity(mcqOptionRequestDTO);

        Question question = questionService.fetchQuestionById(mcqOptionRequestDTO.getQuestionId());
        option.setQuestion(question);

        if (mcqOptionRequestDTO.getImageFile() != null) {
            String imageUrl = cloudinaryService.uploadImage(mcqOptionRequestDTO.getImageFile());
            option.setImage(imageUrl);
        }

        mcqOptionRepository.save(option);

        return McqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public List<McqOptionResponseDTO> createBulkMcqOptions(List<McqOptionRequestDTO> mcqOptionRequestDTOList) {
        List<McqOption> mcqOptions = new ArrayList<>();

        for (McqOptionRequestDTO mcqOptionRequestDTO : mcqOptionRequestDTOList) {
            McqOption option = McqOptionMapper.toEntity(mcqOptionRequestDTO);
            Question question = questionService.fetchQuestionById(mcqOptionRequestDTO.getQuestionId());
            option.setQuestion(question);
            if (mcqOptionRequestDTO.getImageFile() != null) {
                String imageUrl = cloudinaryService.uploadImage(mcqOptionRequestDTO.getImageFile());
                option.setImage(imageUrl);
            }
            mcqOptions.add(option);
        }

        List<McqOption> mcqOptionsEntities = mcqOptionRepository.saveAll(mcqOptions);

        return McqOptionMapper.toResponseDTO(mcqOptionsEntities);
    }

    @Transactional
    public List<McqOptionResponseDTO> createMcqOptionsForQuestion(UUID questionId, List<McqOptionRequestDTO> options) {
        options.forEach(option -> option.setQuestionId(questionId));
        return createBulkMcqOptions(options);
    }

    public List<McqOptionResponseDTO> getOptionsByQuestionId(UUID questionId) {
        List<McqOption> mcqOptions = mcqOptionRepository.findAllByQuestionId(questionId);
        return McqOptionMapper.toResponseDTO(mcqOptions);
    }

    public List<McqOptionResponseDTO> getBulkOptionsByQuestionIds(List<UUID> questionIds) {
        List<McqOption> mcqOptions = mcqOptionRepository.findAllByQuestionIdIn(questionIds);
        return McqOptionMapper.toResponseDTO(mcqOptions);
    }

    public McqOptionResponseDTO getOptionById(UUID optionId) {
        McqOption option = fetchOptionById(optionId);
        return McqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public McqOptionResponseDTO updateOption(UUID optionId, McqOptionRequestDTO mcqOptionRequestDTO) {
        McqOption option = fetchOptionById(optionId);

        McqOptionMapper.updateEntity(option, mcqOptionRequestDTO);

        if (mcqOptionRequestDTO.getImageFile() != null)
            handleImageUpdate(option, mcqOptionRequestDTO.getImageFile());

        mcqOptionRepository.save(option);

        return McqOptionMapper.toResponseDTO(option);
    }

    @Transactional
    public List<McqOptionResponseDTO> updateBulkOptions(List<McqOptionRequestDTO> mcqOptionRequestDTOList) {
        List<McqOption> updatedOptions = mcqOptionRequestDTOList.stream().map(requestDTO -> {
            McqOption option = fetchOptionById(requestDTO.getId());

            McqOptionMapper.updateEntity(option, requestDTO);

            if (requestDTO.getImageFile() != null)
                handleImageUpdate(option, requestDTO.getImageFile());

            return option;
        }).collect(Collectors.toList());

        List<McqOption> mcqOptionEntities = mcqOptionRepository.saveAll(updatedOptions);

        return McqOptionMapper.toResponseDTO(mcqOptionEntities);
    }

    public void deleteOption(UUID optionId) {
        McqOption option = fetchOptionById(optionId);

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
        McqOption option = fetchOptionById(optionId);

        if (option.getImage() != null)
            cloudinaryService.deleteImageByURL(option.getImage());

        option.setImage(null);
        mcqOptionRepository.save(option);
    }

    // <----------- Helpers ----------->

    public McqOption fetchOptionById(UUID optionId) {
        return mcqOptionRepository.findById(optionId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND,"Option not found"));
    }

    private void handleImageUpdate(McqOption option, MultipartFile imageFile) {
        Optional.ofNullable(option.getImage())
                .ifPresent(cloudinaryService::deleteImageByURL);
        String imageUrl = cloudinaryService.uploadImage(imageFile);
        option.setImage(imageUrl);
    }
}
