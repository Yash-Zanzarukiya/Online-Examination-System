package com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamState;

import com.yashpz.examination_system.examination_system.dto.ActiveExam.ActiveExamQuestions.ActiveExamQuestionsDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActiveExamStateDTO {
    private List<ActiveExamQuestionsDTO> questions;
    private Map<UUID, ActiveExamQuestionsState> questionsState;
}
