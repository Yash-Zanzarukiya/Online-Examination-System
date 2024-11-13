package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CollegeRepository extends JpaRepository<College, UUID> {

    College findByName(String name);

    boolean existsByName(String name);

    void deleteByName(String name);
}
