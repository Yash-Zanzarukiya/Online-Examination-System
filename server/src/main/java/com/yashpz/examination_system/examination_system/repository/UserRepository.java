package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Auth;
import com.yashpz.examination_system.examination_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);
}
