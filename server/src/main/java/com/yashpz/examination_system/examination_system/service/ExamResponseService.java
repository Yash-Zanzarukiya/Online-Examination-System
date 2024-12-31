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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamResponseService {
    private final McqSubmissionRepository mcqSubmissionRepository;
    private final ProgrammingSubmissionRepository programmingSubmissionRepository;
    private final ExamAttemptService examAttemptService;
    private final ExamQuestionsService examQuestionsService;
    private final QuestionService questionService;
    private final McqOptionService mcqOptionService;

    @Transactional
    public void saveMcqResponse(McqSubmissionRequestDTO mcqSubmissionRequestDTO, UUID examAttemptId) {
        ExamAttempt examAttempt = examAttemptService.fetchExamAttemptById(examAttemptId);

        UUID questionId = mcqSubmissionRequestDTO.getQuestionId();

        validateQuestionBelongsToExam(questionId, examAttempt);

        McqSubmission mcqSubmission = mcqSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId);

        if (mcqSubmission == null)
            CreateMcqSubmission(mcqSubmissionRequestDTO, examAttempt);
        else
            UpdateMcqSubmission(mcqSubmission, mcqSubmissionRequestDTO);
    }

    @Transactional
    public void saveProgrammingResponse(ProgrammingSubmissionRequestDTO programmingSubmissionRequestDTO, UUID examAttemptId) {
        ExamAttempt examAttempt = examAttemptService.fetchExamAttemptById(examAttemptId);

        UUID questionId = programmingSubmissionRequestDTO.getQuestionId();

        validateQuestionBelongsToExam(questionId, examAttempt);

        ProgrammingSubmission programmingSubmission = programmingSubmissionRepository.findByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId);

        if (programmingSubmission == null)
            CreateProgrammingSubmission(programmingSubmissionRequestDTO, examAttempt);
        else
            UpdateProgrammingSubmission(programmingSubmission, programmingSubmissionRequestDTO);
    }

    @Transactional
    public void updateTimeSpentOnQuestion(UUID questionId, int timeSpent, UUID examAttemptId) {
        ExamAttempt examAttempt = examAttemptService.fetchExamAttemptById(examAttemptId);
        Question question = questionService.fetchQuestionById(questionId);

        validateQuestionBelongsToExam(questionId, examAttempt);

        if (question.getType() == QuestionType.MCQ)
            handleMcqSubmissionTimeSpent(questionId, timeSpent, examAttempt);
        else
            handleProgrammingSubmissionTimeSpent(questionId, timeSpent, examAttempt);
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

    private void handleMcqSubmissionTimeSpent(UUID questionId, int timeSpent, ExamAttempt examAttempt) {
        if (!mcqSubmissionRepository.existsByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId)) {
            Question question = questionService.fetchQuestionById(questionId);
            McqSubmission mcqSubmission = new McqSubmission();
            mcqSubmission.setQuestion(question);
            mcqSubmission.setTimeSpent(timeSpent);
            mcqSubmission.setMarks(0);
            mcqSubmission.setExamAttempt(examAttempt);
            mcqSubmissionRepository.save(mcqSubmission);
        } else {
            mcqSubmissionRepository.updateTimeSpentOnQuestion(examAttempt.getId(), questionId, timeSpent);
        }
    }

    private void handleProgrammingSubmissionTimeSpent(UUID questionId, int timeSpent, ExamAttempt examAttempt) {
        if (!programmingSubmissionRepository.existsByExamAttemptIdAndQuestionId(examAttempt.getId(), questionId)) {
            Question question = questionService.fetchQuestionById(questionId);
            ProgrammingSubmission programmingSubmission = new ProgrammingSubmission();
            programmingSubmission.setQuestion(question);
            programmingSubmission.setTimeSpent(timeSpent);
            programmingSubmission.setMarks(0);
            programmingSubmission.setExamAttempt(examAttempt);
            programmingSubmissionRepository.save(programmingSubmission);
        } else {
            programmingSubmissionRepository.updateTimeSpentOnQuestion(examAttempt.getId(), questionId, timeSpent);
        }
    }
}
