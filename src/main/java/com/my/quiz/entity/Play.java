package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Data
@ToString
@Table(name = "play")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
// 'yes'와 'no' 필드는 이제 개별 기록이므로 필요 없습니다.
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Member 엔티티와 다대일 관계 매핑 (play 여러 개가 한 member에 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member member;

    // Quiz 엔티티와 다대일 관계 매핑 (play 여러 개가 한 quiz에 속함)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    // 정답 횟수 필드
    @Column(nullable = false)
    private int yes = 0;

    // 오답 횟수 필드
    @Column(nullable = false)
    private int no = 0;

    // 퀴즈의 정답 여부를 기록
    @Column(nullable = false)
    private boolean isCorrect;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime updatedAt;
}
