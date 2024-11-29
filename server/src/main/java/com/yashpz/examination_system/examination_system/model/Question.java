package com.yashpz.examination_system.examination_system.model;

import com.yashpz.examination_system.examination_system.constants.Difficulty;
import com.yashpz.examination_system.examination_system.constants.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
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

   @OneToOne
   @JoinColumn(name = "correct_answer_id", nullable = true)
   private McqOption correctAnswer;
}
