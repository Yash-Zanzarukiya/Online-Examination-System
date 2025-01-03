package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.dto.StudentProfile.ExcelStudentData;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentDataFileService {

    public List<String> getEmailsFromStudentDataFile(MultipartFile file) {
        List<String> emails = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                String email = getCellValueAsString(row.getCell(0));
                emails.add(email);
            }
        } catch (IOException e) {
            throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file: " + e.getMessage());
        }
        return emails;
    }

    public List<ExcelStudentData> processStudentDataFile(MultipartFile file) {
        List<ExcelStudentData> studentDataList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                ExcelStudentData studentData = parseStudentRow(row);
                studentDataList.add(studentData);
            }

        } catch (IOException e) {
            throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Error reading file: " + e.getMessage());
        }

        return studentDataList;
    }

    private ExcelStudentData parseStudentRow(Row row) {
        ExcelStudentData studentData = new ExcelStudentData();

        studentData.setEmail(getCellValueAsString(row.getCell(0)));
        studentData.setFullName(getCellValueAsString(row.getCell(1)));
        studentData.setBranch(getCellValueAsString(row.getCell(2)));
        studentData.setPhone(getCellValueAsString(row.getCell(3)));
        studentData.setPassout(parseIntegerValue(row.getCell(4)));

        return studentData;
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> "";
        };
    }

    private Integer parseIntegerValue(Cell cell) {
        try {
            return (int) cell.getNumericCellValue();
        } catch (Exception e) {
            return null;
        }
    }
}
