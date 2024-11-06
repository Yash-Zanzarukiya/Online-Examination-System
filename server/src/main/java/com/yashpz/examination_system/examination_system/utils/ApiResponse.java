package com.yashpz.examination_system.examination_system.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    private final int statusCode;
    private final T data;
    private final String message;
    private final boolean success;

    public ApiResponse(int statusCode, T data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.success = statusCode < 400;
    }

    public ApiResponse(int statusCode, T data) {
        this(statusCode, data, "");
    }

    public ApiResponse(int statusCode, String message) {
        this(statusCode, null, message);
    }
}
