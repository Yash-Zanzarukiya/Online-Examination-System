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
public class QuestionAnswers {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "student_id", referencedColumnName = "id", nullable = false)
   private ExamAttempt examAttempt;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
   private Question question;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "selected_option_id", referencedColumnName = "id", nullable = true)
   private McqOption selectedOption;

   private String answerText;

   private Boolean isCorrect;

   private int timeSpent;

   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

}
