package com.yashpz.examination_system.examination_system.utils;

import com.yashpz.examination_system.examination_system.models.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {

    public static <T> ResponseEntity<ApiResponse<T>> handleResponse(HttpStatus status, T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), data, message);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> handleResponse(HttpStatus status, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), null, message);
        return new ResponseEntity<>(response, status);
    }
}
