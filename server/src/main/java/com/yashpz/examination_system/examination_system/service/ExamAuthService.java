package com.yashpz.examination_system.examination_system.service;

import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.ScheduleExam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.yashpz.examination_system.examination_system.dto.Auth.LoginDTO;
import com.yashpz.examination_system.examination_system.dto.Auth.UserDataDTO;
import com.yashpz.examination_system.examination_system.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExamAuthService {
  private final AuthService authService;
  private final UserService userService;
  private final ScheduleExamService scheduleExamService;
  private final StudentDataFileService studentDataFileService;
  private final EmailService emailService;

  @Value("${EXAM_LOGIN_URL}")
  private String examLoginUrl;

  public User authenticateUser(LoginDTO loginDTO) {
    UserDataDTO userData =  authService.loginUser(loginDTO);
    return userService.fetchUserById(userData.getUserId());
  }

  public List<String> inviteStudentsToExam(UUID scheduledExamId, MultipartFile file) {
    ScheduleExam scheduleExam = scheduleExamService.fetchScheduleExamById(scheduledExamId);

    List<String> emails = studentDataFileService.getEmailsFromStudentDataFile(file);

    String examName = scheduleExam.getName();
    int duration = scheduleExam.getExam().getTimeLimit();
    String testUrl = examLoginUrl + "/" + scheduledExamId;
    String supportEmail = "support@example.com";

    String emailTemplate = loadEmailTemplate();

    for (String email : emails) {
      Map<String, String> placeholders = new HashMap<>();
      placeholders.put("examName", examName);
      placeholders.put("duration", String.valueOf(duration));
      placeholders.put("testUrl", testUrl);
      placeholders.put("supportEmail", supportEmail);

      String emailContent = populateTemplate(emailTemplate, placeholders);

      emailService.sendMail(email, "Invitation to " + examName, emailContent);
    }

    return emails;
  }

  // <----------- Helpers ----------->

  private String loadEmailTemplate() {
    try {
      return new String(Files.readAllBytes(Paths.get("src/main/resources/templates/email-template.html")));
    } catch (IOException e) {
      throw new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load email template");
    }
  }

  private String populateTemplate(String template, Map<String, String> placeholders) {
    for (Map.Entry<String, String> entry : placeholders.entrySet()) {
      template = template.replace("{{" + entry.getKey() + "}}", entry.getValue());
    }
    return template;
  }
}
