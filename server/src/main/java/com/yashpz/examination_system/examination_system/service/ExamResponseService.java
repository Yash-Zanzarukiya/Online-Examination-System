package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.McqSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ExamResponse.ProgrammingSubmissionRequestDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.mappers.McqSubmissionMapper;
import com.yashpz.examination_system.examination_system.mappers.ProgrammingSubmissionMapper;
import com.yashpz.examination_system.examination_system.model.ExamAttempt;
import com.yashpz.examination_system.examination_system.model.McqOption;
import com.yashpz.examination_system.examination_system.model.McqSubmission;
import com.yashpz.examination_system.examination_system.model.ProgrammingSubmission;
import com.yashpz.examination_system.examination_system.model.Question;
import com.yashpz.examination_system.examination_system.repository.McqSubmissionRepository;
import com.yashpz.examination_system.examination_system.repository.ProgrammingSubmissionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ExamResponseService {
    private final McqSubmissionRepository mcqSubmissionRepository;
    private final ProgrammingSubmissionRepository programmingSubmissionRepository;
    private final ExamAttemptService examAttemptService;
    private final ExamQuestionsService examQuestionsService;
    private final QuestionService questionService;
    private final McqOptionService mcqOptionService;

    public ExamResponseService(McqSubmissionRepository mcqSubmissionRepository, ProgrammingSubmissionRepository programmingSubmissionRepository, QuestionService questionService, ExamAttemptService examAttemptService, McqOptionService mcqOptionService, ExamQuestionsService examQuestionsService) {
        this.mcqSubmissionRepository = mcqSubmissionRepository;
        this.programmingSubmissionRepository = programmingSubmissionRepository;
        this.questionService = questionService;
        this.examQuestionsService = examQuestionsService;
        this.examAttemptService = examAttemptService;
        this.mcqOptionService = mcqOptionService;
    }

    @Transactional
    public void saveMcqResponse(McqSubmissionRequestDTO mcqSubmissionRequestDTO) {
        ExamAttempt examAttempt = examAttemptService.fetchCurrentExamAttempt();

        UUID questionId = mcqSubmissionRequestDTO.getQuestionId();

        validateQuestionBelongsToExam(questionId, examAttempt);

        McqSubmission mcqSubmission = mcqSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId);

        if (mcqSubmission == null)
            CreateMcqSubmission(mcqSubmissionRequestDTO, examAttempt);
        else
            UpdateMcqSubmission(mcqSubmission, mcqSubmissionRequestDTO);
    }

    @Transactional
    public void saveProgrammingResponse(ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO) {
        ExamAttempt examAttempt = examAttemptService.fetchCurrentExamAttempt();

        UUID questionId = programmingSubmissionRequestDTO.getQuestionId();

        validateQuestionBelongsToExam(questionId, examAttempt);

        ProgrammingSubmission programmingSubmission = programmingSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId);

        if (programmingSubmission == null)
            CreateProgrammingSubmission(programmingSubmissionRequestDTO, examAttempt);
        else
            UpdateProgrammingSubmission(programmingSubmission, programmingSubmissionRequestDTO);
    }

    @Transactional
    public void updateTimeSpentOnQuestion(UUID questionId, int timeSpent) {
        ExamAttempt examAttempt = examAttemptService.fetchCurrentExamAttempt();
        Question question = questionService.fetchQuestionById(questionId);

        validateQuestionBelongsToExam(questionId, examAttempt);

        Boolean exists = mcqSubmissionRepository.existsByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId);
        if (!exists){
            McqSubmission mcqSubmission = new McqSubmission();
            mcqSubmission.setQuestion(question);
            mcqSubmission.setTimeSpent(timeSpent);
            mcqSubmission.setExamAttempt(examAttempt);
            mcqSubmissionRepository.save(mcqSubmission);
            return;
        }

        if (question.getType()== QuestionType.MCQ)
            mcqSubmissionRepository.updateTimeSpentOnQuestion(examAttempt.getId(), questionId, timeSpent);
        else
            programmingSubmissionRepository.updateTimeSpentOnQuestion(examAttempt.getId(), questionId, timeSpent);
    }

    // <--------------- Helpers --------------->

    private void validateQuestionBelongsToExam(UUID questionId, ExamAttempt examAttempt) {
        Boolean isBelongsToExam = examQuestionsService.isExistsByExamIdAndQuestionId(examAttempt.getExam().getExam().getId(), questionId);
        if (!isBelongsToExam)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Question does not belong to the exam");
    }

    private void CreateMcqSubmission(McqSubmissionRequestDTO mcqSubmissionRequestDTO, ExamAttempt examAttempt) {
        UUID questionId = mcqSubmissionRequestDTO.getQuestionId();
        Question question = questionService.fetchQuestionById(questionId);

        if (question.getType() != QuestionType.MCQ)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Question is not MCQ type");

        UUID selectedOptionId = mcqSubmissionRequestDTO.getSelectedOptionId();
        McqOption mcqOption = mcqOptionService.fetchOptionById(selectedOptionId);

        if (mcqOption.getQuestion().getId() != questionId)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Option is not valid for the question");

        McqSubmission mcqSubmission = McqSubmissionMapper.toEntity(mcqSubmissionRequestDTO, examAttempt, question);
        mcqSubmissionRepository.save(mcqSubmission);
    }

    private void UpdateMcqSubmission(McqSubmission mcqSubmission, McqSubmissionRequestDTO mcqSubmissionRequestDTO) {
        UUID selectedOptionId = mcqSubmissionRequestDTO.getSelectedOptionId();
        McqOption mcqOption = mcqOptionService.fetchOptionById(selectedOptionId);

        if (mcqOption.getQuestion().getId() != mcqSubmission.getQuestion().getId())
            throw new ApiError(HttpStatus.BAD_REQUEST, "Option is not valid for the question");

        McqSubmissionMapper.updateEntity(mcqSubmission, mcqSubmissionRequestDTO);
        mcqSubmissionRepository.save(mcqSubmission);
    }

    private void CreateProgrammingSubmission(ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO, ExamAttempt examAttempt) {
        UUID questionId = programmingSubmissionRequestDTO.getQuestionId();
        Question question = questionService.fetchQuestionById(questionId);

        if (question.getType() != QuestionType.PROGRAMMING)
            throw new ApiError(HttpStatus.BAD_REQUEST, "Question is not Programming type");

        ProgrammingSubmission programmingSubmission = ProgrammingSubmissionMapper.toEntity(programmingSubmissionRequestDTO, examAttempt, question);
        programmingSubmissionRepository.save(programmingSubmission);
    }

    private void UpdateProgrammingSubmission(ProgrammingSubmission programmingSubmission, ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO) {
        ProgrammingSubmissionMapper.updateEntity(programmingSubmission, programmingSubmissionRequestDTO);
        programmingSubmissionRepository.save(programmingSubmission);
    }
}
