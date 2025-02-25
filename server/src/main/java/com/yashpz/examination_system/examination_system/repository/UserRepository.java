package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByAuthId(UUID authId);
    User findByAuthUsername(String username);
    User findByAuthEmail(String email);
    boolean existsByAuthUsername(String username);
    boolean existsByAuthEmail(String email);
}
