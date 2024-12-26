package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.ExamSessionType;
import com.yashpz.examination_system.examination_system.constants.QuestionAttemptStatus;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions.ActiveExamMcqOption;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions.ActiveExamQuestion;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions.ActiveExamQuestionsDTO;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState.ActiveExamQuestionsState;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState.ActiveExamStateDTO;
import com.yashpz.examination_system.examination_system.repository.ExamQuestionsRepository;
import com.yashpz.examination_system.examination_system.repository.McqSubmissionRepository;
import com.yashpz.examination_system.examination_system.repository.ProgrammingSubmissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ActiveExamService {

    private final ExamQuestionsRepository examQuestionsRepository;
    private final McqSubmissionRepository mcqSubmissionRepository;
    private final ProgrammingSubmissionRepository programmingSubmissionRepository;
    private final ExamAttemptService examAttemptService;
    private final ScheduleExamService scheduleExamService;

    public ActiveExamStateDTO getActiveExamState(UUID examAttemptId) {
        Map<UUID, ActiveExamQuestionsState> questionsState = getActiveExamQuestionsState(examAttemptId);
        UUID scheduledExamId = examAttemptService.getScheduledExamIdFromExamAttemptId(examAttemptId);
        List<ActiveExamQuestionsDTO> questions = getActiveExamQuestions(scheduledExamId);
        return new ActiveExamStateDTO(questions, questionsState);
    }

    public ActiveExamStateDTO getActiveExamState(UUID examAttemptId, ExamSessionType examSessionType){
        UUID scheduledExamId = examAttemptService.getScheduledExamIdFromExamAttemptId(examAttemptId);
        List<ActiveExamQuestionsDTO> questions = getActiveExamQuestions(scheduledExamId);

        Map<UUID, ActiveExamQuestionsState> questionsState = new LinkedHashMap<>();
        if (examSessionType == ExamSessionType.RESUMED)
            questionsState = getActiveExamQuestionsState(examAttemptId);

        return new ActiveExamStateDTO(questions, questionsState);
    }

    public List<ActiveExamQuestionsDTO> getActiveExamQuestions(UUID scheduledExamId) {
        UUID examId = scheduleExamService.getExamIdByScheduledExamId(scheduledExamId);
        List<Map<String, Object>> results = examQuestionsRepository.findActiveExamQuestionsByExamId(examId);

        Map<UUID, ActiveExamQuestionsDTO> questionsMap = new LinkedHashMap<>();

        for (Map<String, Object> result : results) {
            UUID questionId = convertToUUID(result.get("question_id"));

            // Create question if not exists
            if (!questionsMap.containsKey(questionId)) {
                ActiveExamQuestion question = new ActiveExamQuestion(
                        questionId,
                        QuestionType.valueOf(result.get("question_type").toString()),
                        Difficulty.valueOf(result.get("question_difficulty").toString()),
                        result.get("question_text").toString(),
                        (String) result.get("question_image")
                );

                questionsMap.put(questionId, new ActiveExamQuestionsDTO(
                        question,
                        new ArrayList<>()
                ));
            }

            // Add options for MCQ questions
            Object optionIdObj = result.get("option_id");
            if (optionIdObj != null &&
                    questionsMap.get(questionId).getQuestion().getType() == QuestionType.MCQ) {
                UUID optionId = convertToUUID(optionIdObj);

                ActiveExamMcqOption option = new ActiveExamMcqOption(
                        optionId,
                        result.get("option_text").toString(),
                        (String) result.get("option_image")
                );
                questionsMap.get(questionId).getOptions().add(option);
            }
        }

        return new ArrayList<>(questionsMap.values());
    }

    public Map<UUID, ActiveExamQuestionsState> getActiveExamQuestionsState(UUID examAttemptId) {
        List<ActiveExamQuestionsState> mcqQuestionState = mcqSubmissionRepository.getMcqSubmissionStateByExamAttemptId(examAttemptId);
        List<ActiveExamQuestionsState> programmingQuestionState = programmingSubmissionRepository.getProgrammingSubmissionStateByExamAttemptId(examAttemptId);

        Map<UUID, ActiveExamQuestionsState> questionsState = new LinkedHashMap<>();
        for (ActiveExamQuestionsState state : mcqQuestionState) {
            QuestionAttemptStatus attemptStatus = state.getSelectedOptionId() == null ? QuestionAttemptStatus.VISITED : QuestionAttemptStatus.ANSWERED;
            state.setStatus(attemptStatus);
            questionsState.put(state.getQuestionId(), state);
        }

        for (ActiveExamQuestionsState state : programmingQuestionState) {
            QuestionAttemptStatus attemptStatus = state.getSubmittedCode() == null ? QuestionAttemptStatus.VISITED : QuestionAttemptStatus.ANSWERED;
            state.setStatus(attemptStatus);
            questionsState.put(state.getQuestionId(), state);
        }

        return questionsState;
    }

    private UUID convertToUUID(Object uuidObject) {
        if (uuidObject == null) return null;

        if (uuidObject instanceof UUID) {
            return (UUID) uuidObject;
        }

        String uuidString = uuidObject.toString();

        // Handle byte array or hex string representations
        if (uuidObject instanceof byte[]) {
            return UUID.nameUUIDFromBytes((byte[]) uuidObject);
        }

        // Remove hyphens and ensure consistent formatting
        uuidString = uuidString.replace("-", "");
        if (uuidString.length() == 32) {
            uuidString = uuidString.substring(0, 8) + "-" +
                    uuidString.substring(8, 12) + "-" +
                    uuidString.substring(12, 16) + "-" +
                    uuidString.substring(16, 20) + "-" +
                    uuidString.substring(20);
        }

        return UUID.fromString(uuidString);
    }
}
