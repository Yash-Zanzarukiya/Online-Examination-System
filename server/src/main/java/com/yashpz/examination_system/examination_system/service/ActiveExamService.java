package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamMcqOption;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestion;
import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestionsDTO;
import com.yashpz.examination_system.examination_system.repository.ExamQuestionsRepository;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ActiveExamService {

    private final ExamQuestionsRepository examQuestionsRepository;

    public ActiveExamService(ExamQuestionsRepository examQuestionsRepository) {
        this.examQuestionsRepository = examQuestionsRepository;
    }

    public List<ActiveExamQuestionsDTO> getQuestionsForExam(UUID examId) {
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
