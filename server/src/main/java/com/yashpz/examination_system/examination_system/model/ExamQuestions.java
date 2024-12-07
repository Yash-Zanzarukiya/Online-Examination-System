package com.yashpz.examination_system.examination_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestions {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "exam_id", referencedColumnName = "id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Exam exam;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private Question question;
}
