package com.yashpz.examination_system.examination_system.config;

import com.yashpz.examination_system.examination_system.models.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ApiResponse<String>> handleApiError(ApiError apiError) {
        ApiResponse<String> response = new ApiResponse<>(
                apiError.getStatusCode(), apiError.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralError(Exception e) {
        ApiResponse<String> response = new ApiResponse<>(500, null, "Internal Server Error : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
