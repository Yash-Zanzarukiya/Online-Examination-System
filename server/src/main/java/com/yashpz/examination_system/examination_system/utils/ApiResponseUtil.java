package com.yashpz.examination_system.examination_system.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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

    public static void setCookie(HttpServletResponse response, String name, String value, int expiry) {
        Cookie jwtCookie = new Cookie(name, value);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(expiry);
        response.addCookie(jwtCookie);
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie jwtCookie = new Cookie(name, null);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
    }
}
