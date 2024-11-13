package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {
    StudentProfile findByUserId(UUID userId);

    StudentProfile findByUser(User user);
}
