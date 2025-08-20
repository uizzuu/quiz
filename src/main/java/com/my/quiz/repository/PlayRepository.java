package com.my.quiz.repository;

import com.my.quiz.entity.Play;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {
    // 특정 회원이 플레이한 퀴즈 수
    long countByMemberNo(Long memberNo);

    // 특정 회원이 맞춘 퀴즈 수
    long countByMemberNoAndIsCorrect(Long memberNo, boolean isCorrect);

    // 특정 퀴즈의 플레이 횟수
    long countByQuizId(Long quizId);

    // 특정 퀴즈의 정답 횟수
    long countByQuizIdAndIsCorrect(Long quizId, boolean isCorrect);

    // 모든 플레이 기록 수
    long count();

    List<Play> findByMemberNoOrderByCreatedAtDesc(Long memberNo);

    // 퀴즈 ID를 기반으로 모든 플레이 기록을 찾는 새로운 메서드입니다.
    List<Play> findByQuizId(Long quizId);
}