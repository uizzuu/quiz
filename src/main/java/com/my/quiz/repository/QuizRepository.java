package com.my.quiz.repository;

import com.my.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByMemberNoOrderByCreatedAtDesc(Long memberNo);
}
