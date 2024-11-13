package com.yashpz.examination_system.examination_system.controller;

import com.yashpz.examination_system.examination_system.dto.Auth.AuthDTO;
import com.yashpz.examination_system.examination_system.dto.Auth.LoginDTO;
import com.yashpz.examination_system.examination_system.dto.User.UserDataDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.service.AuthService;
import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import com.yashpz.examination_system.examination_system.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    @Value("${ACCESS_TOKEN_EXPIRY}")
    private int ACCESS_TOKEN_EXPIRY;

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

        ApiResponseUtil.setCookie(response,"access_token", accessToken,ACCESS_TOKEN_EXPIRY / 1000);

        return ApiResponseUtil.handleResponse(HttpStatus.OK, accessToken,"User Logged In Successfully");
    }

    // TODO: verify JWT token
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserDataDTO>> getCurrentUser() {
        UserDataDTO userData =  authService.getCurrentUser();
        if(userData == null)
            throw new ApiError(HttpStatus.UNAUTHORIZED, "User Not Found");
        return ApiResponseUtil.handleResponse(HttpStatus.OK, userData,"User Details Fetched Successfully");
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(HttpServletResponse response) {
        ApiResponseUtil.deleteCookie(response, "access_token");
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "User Logged Out Successfully");
    }

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verify(@Valid @RequestParam String token) {
        authService.verifyUser(token);
        return ApiResponseUtil.handleResponse(HttpStatus.OK, "User Verified Successfully");
    }

    @GetMapping("/check/username/{username}")
    public ResponseEntity<ApiResponse<Boolean>> checkUniqueUsername(@Valid @PathVariable String username) {
        if(authService.isUsernameAvailable(username))
            return ApiResponseUtil.handleResponse(HttpStatus.OK, true,"Username Available");
        else
            return ApiResponseUtil.handleResponse(HttpStatus.OK, false,"Username Already Taken");
    }
}
