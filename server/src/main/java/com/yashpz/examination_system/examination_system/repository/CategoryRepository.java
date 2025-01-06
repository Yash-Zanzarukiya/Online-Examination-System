package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    boolean existsByName(String name);

    Category findByName(String name);
}
