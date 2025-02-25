package com.yashpz.examination_system.examination_system.utils;

import com.yashpz.examination_system.examination_system.constants.Roles;
import com.yashpz.examination_system.examination_system.dto.Auth.UserDataDTO;
import com.yashpz.examination_system.examination_system.exception.ApiError;
import com.yashpz.examination_system.examination_system.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ResourceAccessUtil {
    private final AuthService authService;

    public ResourceAccessUtil(AuthService authService) {
        this.authService = authService;
    }

    public void OwnerOnlyAccess(UUID userId) {
        boolean equals = authService.getCurrentUser().getId().toString().equals(userId.toString());
        if (!equals)
            throw new ApiError(HttpStatus.FORBIDDEN, "Access Denied, Only Owner Can Access");
    }

    public void AdminOnlyAccess() {
        boolean equals = authService.getCurrentUser().getRole().equals(Roles.ADMIN.name());
        if (!equals)
            throw new ApiError(HttpStatus.FORBIDDEN, "Access Denied, Only Admin Can Access");
    }

    public void AdminOrOwnerAccess(UUID userId) {
        UserDataDTO currentUser = authService.getCurrentUser();
        boolean isAdmin = currentUser.getRole().equals(Roles.ADMIN.name());
        boolean isOwner = currentUser.getId().toString().equals(userId.toString());
        if (!isAdmin && !isOwner)
            throw new ApiError(HttpStatus.FORBIDDEN, "Access Denied, Only Admin Or Owner Can Access");
    }
}
