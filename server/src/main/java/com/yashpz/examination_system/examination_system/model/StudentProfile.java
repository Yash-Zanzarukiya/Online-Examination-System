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
public class StudentProfile {

   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   private UUID id;

   @OneToOne
   @JoinColumn(name = "[user]", referencedColumnName = "id", nullable = false)
   private User user;

   private String fullName;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "college", referencedColumnName = "id", nullable = false)
   private College college;

   private String branch;

   private String phone;

   private int passout;

   @CreatedDate
   @Column(nullable = false, updatable = false)
   private LocalDateTime createdAt;

   @LastModifiedDate
   private LocalDateTime updatedAt;
}
