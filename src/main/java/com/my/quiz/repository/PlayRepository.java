package com.my.quiz.repository;

import com.my.quiz.entity.Play;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayRepository extends JpaRepository<Play, Long> {
    List<Play> findByMemberNoOrderByCreatedAtDesc(Long memberNo);
}
