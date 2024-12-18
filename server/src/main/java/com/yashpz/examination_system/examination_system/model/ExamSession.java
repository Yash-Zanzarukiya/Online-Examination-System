package com.yashpz.examination_system.examination_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
public class ExamSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exam_attempt_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ExamAttempt examAttempt;

    @Lob
    @Column(nullable = true)
    private String sessionToken;

    @Column(nullable = false)
    private String visitorId;

    @Column(nullable = false)
    private LocalDateTime lastPing;

    @Column(nullable = true, columnDefinition = "bit default 0")
    private Boolean isDisconnected;

    @Column(nullable = true, columnDefinition = "int default 0")
    private Integer disconnectCount;

    @Column(nullable = false)
    private Long remainingTime;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
