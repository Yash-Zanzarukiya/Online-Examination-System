package com.yashpz.examination_system.examination_system.model;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
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
public class Question {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
   private Category category;

   @Enumerated(EnumType.STRING)
   private Difficulty difficulty;

   @Enumerated(EnumType.STRING)
   private QuestionType type;

   @Column(nullable = false)
   private String questionText;

   private String image;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "correct_answer_id", referencedColumnName = "id")
   private McqOption correctAnswer;

   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;

}
