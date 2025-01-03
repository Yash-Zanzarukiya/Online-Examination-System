package com.yashpz.examination_system.examination_system.repository;

import com.yashpz.examination_system.examination_system.model.StudentProfile;
import com.yashpz.examination_system.examination_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentProfileRepository extends JpaRepository<StudentProfile, UUID> {
    StudentProfile findByUserId(UUID userId);

    StudentProfile findByUser(User user);

    @Query("SELECT sp FROM StudentProfile sp WHERE (:collegeId IS NULL OR sp.college.id = :collegeId) AND (:passout IS NULL OR sp.passout = :passout)")
    List<StudentProfile> findAllByFilters(@Param("collegeId") UUID collegeId, @Param("passout") Integer passout);
}
