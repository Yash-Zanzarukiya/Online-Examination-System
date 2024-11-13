package com.yashpz.examination_system.examination_system.model;

import com.yashpz.examination_system.examination_system.constants.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "exam_id", referencedColumnName = "id", nullable = false)
   private Exam exam;

   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
   private Question question;

   @Enumerated(EnumType.STRING)
   private QuestionType questionType;

}
