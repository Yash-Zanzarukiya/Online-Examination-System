package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthRepository extends JpaRepository<Auth, UUID> {
    Auth findByUsername(String username);
    Auth findByEmail(String email);
    Auth findByUsernameOrEmail(String username, String email);
}

