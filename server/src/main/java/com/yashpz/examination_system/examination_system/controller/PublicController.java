package com.yashpz.examination_system.examination_system.controller;


import com.yashpz.examination_system.examination_system.models.ApiResponse;
import com.yashpz.examination_system.examination_system.utils.ApiError;
import com.yashpz.examination_system.examination_system.utils.ApiResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicController {

    @GetMapping("health")
    public ResponseEntity<ApiResponse<String>> checkHealth(){
        return ApiResponseUtil.handleResponse(HttpStatus.OK,"SERVER IS HEALTHY.");
    }
 }
