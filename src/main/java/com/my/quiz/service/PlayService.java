package com.my.quiz.service;

import com.my.quiz.dto.PlayDto;
import com.my.quiz.entity.Play;
import com.my.quiz.repository.PlayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayService {

    private final PlayRepository repository;

    public PlayService(PlayRepository repository) {
        this.repository = repository;
    }

    // 특정 회원의 플레이 기록 조회
    public List<PlayDto> findByMemberNo(Long memberNo) {
        List<Play> plays = repository.findByMemberNoOrderByCreatedAtDesc(memberNo);
        return plays.stream()
                .map(PlayDto::fromPlayEntity)
                .toList();
    }

    // 모든 플레이 기록 조회
    public List<PlayDto> getAllPlays() {
        List<Play> plays = repository.findAll();
        return plays.stream()
                .map(PlayDto::fromPlayEntity)
                .toList();
    }

    // 통계용 메소드
    public long getTotalPlayCount() {
        return repository.count();
    }
}