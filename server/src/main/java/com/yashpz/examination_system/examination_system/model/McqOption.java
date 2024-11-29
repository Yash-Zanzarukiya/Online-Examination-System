package com.yashpz.examination_system.examination_system.model;

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
public class McqOption {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private UUID id;

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
   private Question question;

   @Column(nullable = false)
   private String optionText;

   private String image;

   @PreRemove
   private void preRemove() {
      if (question != null && question.getCorrectAnswer() != null && question.getCorrectAnswer().equals(this)) {
         question.setCorrectAnswer(null);
      }
   }
}
