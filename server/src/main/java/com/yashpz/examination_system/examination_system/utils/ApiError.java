package com.yashpz.examination_system.examination_system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError extends RuntimeException {
    private boolean success;
    private String message;
    private int statusCode;
    private List<String> errors; // Assuming you want to store multiple error messages
    private Object data;

    public ApiError(HttpStatus statusCode, String message) {
        this(statusCode, message, null, null);
    }

    public ApiError(HttpStatus statusCode, String message, List<String> errors, Object data) {
        super(message);
        this.success = false;
        this.message = message;
        this.statusCode = statusCode.value();
        this.errors = errors;
        this.data = data;
    }
}
