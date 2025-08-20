package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
// 이 어노테이션을 추가해야 @CreatedDate와 @LastModifiedDate가 작동합니다.
@EntityListeners(AuditingEntityListener.class)
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 500, nullable = false)
    private String content;
    // true: 정답이 '예', false: 정답이 '아니오'
    private Boolean answer;
    // 작성자 정보
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
