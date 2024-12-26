package com.yashpz.examination_system.examination_system.service;

import org.springframework.stereotype.Service;

import com.yashpz.examination_system.examination_system.dto.Auth.LoginDTO;
import com.yashpz.examination_system.examination_system.dto.Auth.UserDataDTO;
import com.yashpz.examination_system.examination_system.model.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExamAuthService {
  private final AuthService authService;
  private final UserService userService;

  public User authenticateUser(LoginDTO loginDTO) {
    UserDataDTO userData =  authService.loginUser(loginDTO);
    return userService.fetchUserById(userData.getUserId());
  }
}
