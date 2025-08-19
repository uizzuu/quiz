package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Member 엔티티와 다대일 관계 매핑 (play 여러 개가 한 member에 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    // member 테이블의 기본키 no를 참조
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;
    @Column(nullable = false)
    private int yes = 0; // 맞은 수
    @Column(nullable = false)
    private int no = 0; // 틀린 수

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
