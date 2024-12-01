package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Question.*;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.QuestionMapper;
import com.yashpz.examination_system.examination_system.model.Category;
import com.yashpz.examination_system.examination_system.model.McqOption;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.CategoryRepository;
import com.yashpz.examination_system.examination_system.repository.McqOptionRepository;
import com.yashpz.examination_system.examination_system.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final QuestionMapper questionMapper;
    private final CloudinaryService cloudinaryService;
    private final McqOptionRepository mcqOptionRepository;

    public QuestionService(QuestionRepository questionRepository, CategoryRepository categoryRepository, QuestionMapper questionMapper, CloudinaryService cloudinaryService, McqOptionRepository mcqOptionRepository) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.questionMapper = questionMapper;
        this.cloudinaryService = cloudinaryService;
        this.mcqOptionRepository = mcqOptionRepository;
    }

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionDTO questionDTO) {
        Question question = questionMapper.toEntity(questionDTO);

        if(question.getType() == QuestionType.MCQ)
            updateCategory(question, questionDTO.getCategoryId());

        if (questionDTO.getImageFile() != null) {
            String imageUrl = cloudinaryService.uploadImage(questionDTO.getImageFile());
            question.setImage(imageUrl);
        }

        questionRepository.save(question);

        return questionMapper.toResponseDTO(question);
    }

    @Transactional
    public List<QuestionResponseDTO> createQuestion(List<QuestionDTO> questionDTOList) {
        return questionDTOList.stream()
                .map(this::createQuestion)
                .toList();
    }

    public QuestionResponseDTO getQuestionById(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST,"Invalid question ID"));
        return questionMapper.toResponseDTO(question);
    }

    public Question getQuestionEntityById(UUID questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND,"Question not found"));
    }

    public List<QuestionResponseDTO> getAllQuestions(UUID categoryId, Difficulty difficulty, QuestionType type) {
        return questionRepository.findAllByFilters(categoryId, difficulty, type).stream()
                .map(questionMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public QuestionResponseDTO updateQuestion(UUID questionId, QuestionDTO questionDTO, MultipartFile imageFile) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST,"Invalid question ID"));

        questionMapper.updateEntity(question, questionDTO);

        if (questionDTO.getCategoryId() != null)
            updateCategory(question, questionDTO.getCategoryId());

        if (questionDTO.getCorrectAnswerId() != null)
            updateCorrectAnswer(question.getId(), questionDTO.getCorrectAnswerId());

        if (imageFile != null) {
            if (question.getImage() != null)
                cloudinaryService.deleteImageByURL(question.getImage());

            String imageUrl = cloudinaryService.uploadImage(imageFile);
            question.setImage(imageUrl);
        }

        questionRepository.save(question);

        return questionMapper.toResponseDTO(question);
    }

    public void updateCorrectAnswer(UUID questionId, UUID correctAnswerId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST,"Invalid question ID"));

        McqOption correctAnswer = mcqOptionRepository.findById(correctAnswerId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid Option ID"));

        question.setCorrectAnswer(correctAnswer);

        questionRepository.save(question);
    }

    public void updateCategory(Question question, UUID newCategoryId) {
        Category category = categoryRepository.findById(newCategoryId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST,"Invalid category ID"));
        question.setCategory(category);
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid question ID"));

        if (question.getImage() != null)
            cloudinaryService.deleteImageByURL(question.getImage());

        List<McqOption> options = mcqOptionRepository.findAllByQuestionId(questionId);

        options.forEach((option) -> {
            cloudinaryService.deleteImageByURL(option.getImage());
            mcqOptionRepository.deleteById(option.getId());
        });

        questionRepository.deleteById(questionId);
    }

    public void deleteQuestionImage(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST, "Invalid question ID"));

        if (question.getImage() != null) {
            cloudinaryService.deleteImageByURL(question.getImage());
            question.setImage(null);
            questionRepository.save(question);
        }
    }
}
