package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.AuthDTO;
import com.yashpz.examination_system.examination_system.dto.LoginDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.service.AuthService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import com.yashpz.examination_system.examination_system.utils.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody AuthDTO user) {
        authService.registerUser(user);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>>  login(@Valid @RequestBody LoginDTO user, HttpServletResponse response) {
        Auth auth = authService.loginUser(user);

        if(auth == null)
            throw new ApiError(HttpStatus.BAD_REQUEST, "User Not Found");

        String accessToken = jwtUtil.generateToken(auth.getUsername());

        ApiResponseUtil.setJwtCookie(response, accessToken);

        return ApiResponseUtil.handleResponse(HttpStatus.OK, accessToken,"User Logged In Successfully");
    }

    @PostMapping("verify")
    public ResponseEntity<ApiResponse<String>> verify(@Valid @RequestBody String token) {
        authService.verifyUser(token);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "User Verified Successfully");
    }
}
