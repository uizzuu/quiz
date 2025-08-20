package com.my.quiz.repository;

import com.my.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {
    // memberNo로 퀴즈 개수 조회
    long countByMemberNo(Long memberNo);

    // memberNo로 퀴즈 목록 조회
    List<Quiz> findByMemberNoOrderByCreatedAtDesc(Long memberNo);

    // Quiz ID로 퀴즈를 찾는 메서드 추가
    Optional<Quiz> findById(Long id);
}