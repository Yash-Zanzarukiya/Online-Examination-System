package com.yashpz.examination_system.examination_system.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError extends RuntimeException {
    private int statusCode;
    private String message;
    private Object data;
    private boolean success;

    public ApiError(HttpStatus statusCode, String message) {
        this(statusCode, message, null);
    }

    public ApiError(HttpStatus statusCode, String message, Object data) {
        super(message);
        this.success = false;
        this.message = message;
        this.statusCode = statusCode.value();
        this.data = data;
    }
}
