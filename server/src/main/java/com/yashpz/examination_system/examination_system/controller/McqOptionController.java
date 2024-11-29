package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Question.McqOptionDTO;
import com.yashpz.examination_system.examination_system.dto.Question.McqOptionResponseDTO;
import com.yashpz.examination_system.examination_system.service.McqOptionService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/mcq-options")
public class McqOptionController {

    private final McqOptionService mcqOptionService;

    public McqOptionController(McqOptionService mcqOptionService) {
        this.mcqOptionService = mcqOptionService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<McqOptionResponseDTO>> createMcqOption(@Valid @RequestBody McqOptionDTO mcqOptionDTO) throws IOException {
        McqOptionResponseDTO mcqOption = mcqOptionService.createMcqOption(mcqOptionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, mcqOption, "Mcq option created successfully");
    }

    @PostMapping("/multiple")
    public ResponseEntity<ApiResponse<List<McqOptionResponseDTO>>> createMultipleMcqOptions(@RequestBody @Valid List<McqOptionDTO> mcqOptionDTOList) {
        List<McqOptionResponseDTO> mcqOptions = mcqOptionService.createMultipleMcqOptions(mcqOptionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.CREATED, mcqOptions, "Mcq options created successfully");
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<List<McqOptionResponseDTO>>> getOptionsByQuestionId(@PathVariable UUID questionId) {
        List<McqOptionResponseDTO> options = mcqOptionService.getOptionsByQuestionId(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, options, "Options fetched successfully");
    }

    @GetMapping("/{optionId}")
    public ResponseEntity<ApiResponse<McqOptionResponseDTO>> getOptionById(@PathVariable UUID optionId) {
        McqOptionResponseDTO option = mcqOptionService.getOptionById(optionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, option, "Option fetched successfully");
    }

    @PatchMapping("/{optionId}")
    public ResponseEntity<ApiResponse<McqOptionResponseDTO>> updateOption(@PathVariable UUID optionId, @RequestBody @Valid McqOptionDTO mcqOptionDTO) throws IOException {
        McqOptionResponseDTO updateOption = mcqOptionService.updateOption(optionId, mcqOptionDTO);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updateOption, "Option updated successfully");
    }

    @PatchMapping("/multiple")
    public ResponseEntity<ApiResponse<List<McqOptionResponseDTO>>> updateMultipleOptions(@RequestBody @Valid List<McqOptionDTO> mcqOptionDTOList) {
        List<McqOptionResponseDTO> updatedOptions = mcqOptionService.updateMultipleOptions(mcqOptionDTOList);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, updatedOptions, "Options updated successfully");
    }

    @DeleteMapping("/{optionId}")
    public ResponseEntity<ApiResponse<String>> deleteOption(@PathVariable UUID optionId) {
        mcqOptionService.deleteOption(optionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Option deleted successfully");
    }

    @DeleteMapping("/{optionId}/image")
    public ResponseEntity<ApiResponse<String>> deleteOptionImage(@PathVariable UUID optionId) {
        mcqOptionService.deleteOptionImage(optionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Option image deleted successfully");
    }

    @DeleteMapping("/question/{questionId}")
    public ResponseEntity<ApiResponse<String>> deleteOptionsByQuestionId(@PathVariable UUID questionId) {
        mcqOptionService.deleteOptionsByQuestionId(questionId);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "Options deleted successfully");
    }
}
