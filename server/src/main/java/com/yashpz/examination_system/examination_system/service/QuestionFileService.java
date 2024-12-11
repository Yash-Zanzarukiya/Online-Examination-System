package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import com.yashpz.examination_system.examination_system.dto.McqOption.McqOptionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.McqQuestion.McqQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.ProgrammingQuestion.ProgrammingQuestionRequestDTO;
import com.yashpz.examination_system.examination_system.dto.Question.QuestionRequestDTO;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class QuestionFileService {
    private final McqQuestionService mcqQuestionService;
    private final ProgrammingQuestionService programmingQuestionService;

    public QuestionFileService(McqQuestionService mcqQuestionService, ProgrammingQuestionService programmingQuestionService) {
        this.mcqQuestionService = mcqQuestionService;
        this.programmingQuestionService = programmingQuestionService;
    }

    @Transactional
    public void processQuestionsFile(MultipartFile file) {
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet questionSheet = workbook.getSheetAt(0);
            Sheet optionSheet = workbook.getSheetAt(1);

            Map<Integer, McqQuestionRequestDTO> mcqQuestionMap = new HashMap<>();
            List<ProgrammingQuestionRequestDTO> programmingQuestions = new ArrayList<>();

            // Parse questions
            for (Row row : questionSheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                int srNo = (int) row.getCell(0).getNumericCellValue();
                QuestionRequestDTO question = parseQuestion(row);

                if (question.getType() == QuestionType.MCQ) {
                    McqQuestionRequestDTO mcq = new McqQuestionRequestDTO();
                    mcq.setQuestion(question);
                    mcq.setOptions(new ArrayList<>());
                    mcqQuestionMap.put(srNo, mcq);
                } else if (question.getType() == QuestionType.PROGRAMMING) {
                    ProgrammingQuestionRequestDTO programming = new ProgrammingQuestionRequestDTO();
                    programming.setQuestion(question);
                    programming.setReferenceAnswer(getCellValueAsString(row.getCell(6))); // Reference answer
                    programmingQuestions.add(programming);
                }
            }

            // Parse options
            for (Row row : optionSheet) {
                if (row.getRowNum() == 0) continue; // Skip header row

                int srNo = (int) row.getCell(0).getNumericCellValue();
                String optionText = getCellValueAsString(row.getCell(1));
                Boolean isCorrect = Boolean.valueOf(getCellValueAsString(row.getCell(2)));

                McqOptionRequestDTO option = new McqOptionRequestDTO();
                option.setOptionText(optionText);
                option.setIsCorrect(isCorrect);

                // Adding option to the corresponding MCQ question
                if (mcqQuestionMap.containsKey(srNo)) {
                    mcqQuestionMap.get(srNo).getOptions().add(option);
                }
            }

            mcqQuestionService.createBulkMcqQuestions(new ArrayList<>(mcqQuestionMap.values()));
            programmingQuestionService.createBulkProgrammingQuestions(programmingQuestions);

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
    }

    private QuestionRequestDTO parseQuestion(Row row) {
        QuestionRequestDTO question = new QuestionRequestDTO();
        question.setDifficulty(Difficulty.valueOf(getCellValueAsString(row.getCell(3))));
        question.setType(QuestionType.valueOf(getCellValueAsString(row.getCell(1)).toUpperCase()));
        if (question.getType() == QuestionType.MCQ)
            question.setCategoryId(UUID.fromString(getCellValueAsString(row.getCell(2))));
        question.setQuestionText(getCellValueAsString(row.getCell(4)));
        question.setMarks(Integer.parseInt(getCellValueAsString(row.getCell(5))));
        return question;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }
}
