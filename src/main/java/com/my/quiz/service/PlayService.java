package com.my.quiz.service;

import com.my.quiz.dto.PlayDto;
import com.my.quiz.entity.Member;
import com.my.quiz.entity.Play;
import com.my.quiz.repository.MemberRepository;
import com.my.quiz.repository.PlayRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayService {

    @Autowired
    private PlayRepository playRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Transactional
    public void savePlay(Long memberNo, boolean isCorrect) {
        // 1. 회원의 존재 여부를 확인합니다.
        Member member = memberRepository.findById(memberNo)
                .orElseThrow(() -> new IllegalArgumentException("회원 번호가 올바르지 않습니다: " + memberNo));

        // 2. 새로운 플레이 기록 엔티티를 생성합니다.
        // 각 퀴즈 플레이는 별도의 기록으로 저장되어야 합니다.
        Play play = new Play();
        play.setMember(member);

        // 3. 정답 또는 오답 횟수를 설정합니다.
        if (isCorrect) {
            play.setYes(1);
            play.setNo(0);
        } else {
            play.setYes(0);
            play.setNo(1);
        }

        // 4. 새로운 엔티티를 데이터베이스에 저장합니다.
        // 이렇게 하면 기록이 누적되어 myRecord 페이지에 여러 줄로 표시됩니다.
        playRepository.save(play);
    }

    // 통계용 메소드 - 전체 플레이 기록 수 반환
    public long getTotalPlayCount() {
        return playRepository.count();
    }

    // 특정 회원의 플레이 기록을 최신순으로 조회
    public List<PlayDto> findByMemberNo(Long memberNo) {
        List<Play> plays = playRepository.findByMemberNoOrderByCreatedAtDesc(memberNo);
        return plays.stream()
                .map(PlayDto::fromPlayEntity)
                .collect(Collectors.toList());
    }
}