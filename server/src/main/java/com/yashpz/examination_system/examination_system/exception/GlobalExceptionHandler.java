package com.yashpz.examination_system.examination_system.exception;

import com.yashpz.examination_system.examination_system.utils.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // This Handles validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // This Handles custom API errors
    @ExceptionHandler(ApiError.class)
    public ResponseEntity<ApiResponse<String>> handleApiError(ApiError apiError) {
        ApiResponse<String> response = new ApiResponse<>(
                apiError.getStatusCode(), apiError.getMessage());
        return new ResponseEntity<>(response, HttpStatus.valueOf(apiError.getStatusCode()));
    }

    // This Handles generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneralError(Exception e) {
        ApiResponse<String> response = new ApiResponse<>(500, null, "Internal Server Error : " + e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
