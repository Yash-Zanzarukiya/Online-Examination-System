package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.Question.*;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.QuestionMapper;
import com.yashpz.examination_system.examination_system.model.Category;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.CategoryRepository;
import com.yashpz.examination_system.examination_system.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    public QuestionService(QuestionRepository questionRepository, CategoryRepository categoryRepository, CloudinaryService cloudinaryService) {
        this.questionRepository = questionRepository;
        this.categoryRepository = categoryRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Transactional
    public QuestionResponseDTO createQuestion(QuestionRequestDTO questionRequestDTO) {
        Question question = QuestionMapper.toEntity(questionRequestDTO);

        if(question.getType() == QuestionType.MCQ)
            updateCategory(question, questionRequestDTO.getCategoryId());

        if (questionRequestDTO.getImageFile() != null) {
            String imageUrl = cloudinaryService.uploadImage(questionRequestDTO.getImageFile());
            question.setImage(imageUrl);
        }

        questionRepository.save(question);

        return QuestionMapper.toResponseDTO(question);
    }

    @Transactional
    public List<QuestionResponseDTO> createBulkQuestions(List<QuestionRequestDTO> questionRequestDTOList) {
        List<Question> questions = new ArrayList<>();

        questionRequestDTOList.forEach(reqDTO -> {
            Question question = QuestionMapper.toEntity(reqDTO);

            if(reqDTO.getType() == QuestionType.MCQ)
                updateCategory(question, reqDTO.getCategoryId());

            if (reqDTO.getImageFile() != null) {
                String imageUrl = cloudinaryService.uploadImage(reqDTO.getImageFile());
                question.setImage(imageUrl);
            }
            questions.add(question);
        });

        List<Question> questionsEntities = questionRepository.saveAll(questions);

        return QuestionMapper.toResponseDTO(questionsEntities);
    }

    public Question getQuestionEntityById(UUID questionId) {
        return fetchQuestionById(questionId);
    }

    public QuestionResponseDTO getQuestionById(UUID questionId) {
        Question question = fetchQuestionById(questionId);
        return QuestionMapper.toResponseDTO(question);
    }

    public List<QuestionResponseDTO> getAllQuestions(UUID categoryId, Difficulty difficulty, QuestionType type) {
        List<Question> questions = questionRepository.findAllByFilters(categoryId, difficulty, type);
        return QuestionMapper.toResponseDTO(questions);
    }

    @Transactional
    public QuestionResponseDTO updateQuestion(UUID questionId, QuestionRequestDTO questionRequestDTO) {
        Question question = fetchQuestionById(questionId);

        QuestionMapper.updateEntity(question, questionRequestDTO);

        if (questionRequestDTO.getCategoryId() != null)
            updateCategory(question, questionRequestDTO.getCategoryId());

        if (questionRequestDTO.getImageFile() != null)
            handleImageUpdate(question, questionRequestDTO.getImageFile());

        questionRepository.save(question);

        return QuestionMapper.toResponseDTO(question);
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = fetchQuestionById(questionId);

        if (question.getImage() != null)
            cloudinaryService.deleteImageByURL(question.getImage());

        //  TODO : Check if it is working or not

//        List<McqOption> options = mcqOptionRepository.findAllByQuestionId(questionId);
//
//        options.forEach((option) -> {
//            cloudinaryService.deleteImageByURL(option.getImage());
//            mcqOptionRepository.deleteById(option.getId());
//        });

        questionRepository.deleteById(questionId);
    }

    @Transactional
    public void deleteQuestionImage(UUID questionId) {
        Question question = fetchQuestionById(questionId);

        if (question.getImage() != null) {
            cloudinaryService.deleteImageByURL(question.getImage());
            question.setImage(null);
            questionRepository.save(question);
        }
    }

    // <----------- Helpers ----------->

    private void updateCategory(Question question, UUID categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ApiError(HttpStatus.BAD_REQUEST,"Invalid category ID"));
        question.setCategory(category);
    }

    private Question fetchQuestionById(UUID questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND,"Question not found"));
    }

    private void handleImageUpdate(Question question, MultipartFile imageFile) {
        Optional.ofNullable(question.getImage())
                .ifPresent(cloudinaryService::deleteImageByURL);
        String imageUrl = cloudinaryService.uploadImage(imageFile);
        question.setImage(imageUrl);
    }
}
