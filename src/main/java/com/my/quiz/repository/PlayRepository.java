package com.my.quiz.repository;

import com.my.quiz.entity.Member;
import com.my.quiz.entity.Play;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayRepository extends JpaRepository<Play, Long> {
    List<Play> findByMemberNoOrderByCreatedAtDesc(Long memberNo);

    // Member 엔티티로 Play 기록을 찾는 메소드 추가
    Optional<Play> findByMember(Member member);
}