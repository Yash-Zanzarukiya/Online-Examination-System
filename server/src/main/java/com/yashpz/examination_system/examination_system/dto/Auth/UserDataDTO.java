package com.yashpz.examination_system.examination_system.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDataDTO {
    private UUID id;
    private UUID userId;
    private String username;
    private String email;
    private String role;
    private String fullName;
}
