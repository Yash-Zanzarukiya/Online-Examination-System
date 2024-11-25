package com.yashpz.examination_system.examination_system.mappers;

import com.yashpz.examination_system.examination_system.dto.User.UserDataDTO;
import com.yashpz.examination_system.examination_system.model.Auth;

public class AuthDataMapper {
    public static UserDataDTO toUserDataDTO(Auth auth) {
        return new UserDataDTO(
                auth.getId(),
                auth.getUser().getId(),
                auth.getUsername(),
                auth.getEmail(),
                auth.getUser().getRole().name(),
                auth.getUser().getFullName()
        );
    }
}
