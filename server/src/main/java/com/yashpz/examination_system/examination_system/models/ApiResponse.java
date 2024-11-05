package com.yashpz.examination_system.examination_system.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int statusCode;
    private T data;
    private String message;
    private boolean success;

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
