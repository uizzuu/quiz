package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor // 이 생성자를 추가하여 @Builder와 함께 사용 가능하게 합니다.
@Builder // Member 클래스에 @Builder 어노테이션을 추가합니다.
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;
    @Column(unique = true, nullable = false, length = 50)
    private String id;
    @Column(nullable = false)
    private String password;
    private Boolean status = false;
    @Column(nullable = false)
    private int answerTrue = 0; // 맞춘 문제 수

    @Column(nullable = false)
    private int answerFalse = 0; // 틀린 문제 수

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private String role;

    // Member가 작성한 Quiz 목록 (toString 순환 참조 방지)
    @ToString.Exclude
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Quiz> quizzes = new ArrayList<>();

    // Member가 플레이한 기록 목록 (toString 순환 참조 방지)
    @ToString.Exclude
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Play> plays = new ArrayList<>();
}
