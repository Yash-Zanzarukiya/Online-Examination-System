package com.yashpz.examination_system.examination_system.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiResponseUtil {

    @Value("${app.ACCESS_TOKEN_EXPIRY}")
    private static int ACCESS_TOKEN_EXPIRY;

    public static <T> ResponseEntity<ApiResponse<T>> handleResponse(HttpStatus status, T data, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), data, message);
        return new ResponseEntity<>(response, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> handleResponse(HttpStatus status, String message) {
        ApiResponse<T> response = new ApiResponse<>(status.value(), null, message);
        return new ResponseEntity<>(response, status);
    }

    public static void setJwtCookie(HttpServletResponse response, String token) {
        Cookie jwtCookie = new Cookie("access_token", token);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(ACCESS_TOKEN_EXPIRY / 1000);

        response.addCookie(jwtCookie);
    }
}
