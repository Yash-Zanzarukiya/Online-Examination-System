package com.yashpz.examination_system.examination_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamResult {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "exam_id", referencedColumnName = "id", nullable = false)
   private Exam exam;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
   private User student;

   private Integer score;

   @Column(nullable = false)
   private Boolean isPassed;

   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

}
