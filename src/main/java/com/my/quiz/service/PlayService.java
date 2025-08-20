package com.my.quiz.service;

import com.my.quiz.dto.PlayDto;
import com.my.quiz.entity.Member;
import com.my.quiz.entity.Play;
import com.my.quiz.repository.MemberRepository;
import com.my.quiz.repository.PlayRepository;
import com.my.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayService {

    @Autowired
    private PlayRepository playRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Transactional
    public void savePlay(Long quizId, Long memberNo, boolean isCorrect) {
        // 기존 Play 엔티티 수정에 따라 PlayDto와 관련 로직은 제거

        // 1. 회원이 존재하는지 확인합니다.
        Member member = memberRepository.findById(memberNo).orElse(null);
        if (member == null) {
            throw new IllegalArgumentException("올바르지 않은 회원 번호입니다: " + memberNo);
        }

        // 2. 퀴즈가 존재하는지 확인합니다.
        // QuizService를 직접 사용하기 위해 @Autowired 추가
        com.my.quiz.entity.Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz == null) {
            throw new IllegalArgumentException("올바르지 않은 퀴즈 번호입니다: " + quizId);
        }

        // 3. 새로운 Play 엔티티를 생성합니다. 각 퀴즈 시도는 새로운 기록이어야 합니다.
        Play play = new Play();
        play.setMember(member);
        play.setQuiz(quiz); // Play 엔티티에 퀴즈 엔티티를 설정합니다.

        // 4. 이 특정 플레이에 대한 정답 또는 오답 횟수를 설정합니다.
        if (isCorrect) {
            play.setYes(1);
            play.setNo(0);
        } else {
            play.setYes(0);
            play.setNo(1);
        }

        // 5. 새로운 엔티티를 데이터베이스에 저장합니다.
        // 'play' 테이블에 새로운 행을 삽입합니다.
        playRepository.save(play);
    }

    // 모든 플레이 기록의 총 개수를 가져옵니다.
    public long getTotalPlayCount() {
        return playRepository.count();
    }

    // 특정 회원의 플레이 기록 목록을 최신 생성일 순으로 찾아 반환합니다.
    public List<PlayDto> findByMemberNo(Long memberNo) {
        List<Play> plays = playRepository.findByMemberNoOrderByCreatedAtDesc(memberNo);
        return plays.stream()
                .map(PlayDto::fromPlayEntity)
                .collect(Collectors.toList());
    }

    // 회원별 총 플레이 수
    public long getTotalPlaysByMemberNo(Long memberNo) {
        return playRepository.countByMemberNo(memberNo);
    }

    // 회원별 정답률 계산
    public double getCorrectRateByMemberNo(Long memberNo) {
        long totalPlays = playRepository.countByMemberNo(memberNo);
        if (totalPlays == 0) {
            return 0.0;
        }
        long correctPlays = playRepository.countByMemberNoAndIsCorrect(memberNo, true);
        return (double) correctPlays / totalPlays * 100;
    }

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    /**
     * 퀴즈 ID를 기반으로 정답률을 계산합니다.
     * @param quizId 정답률을 계산할 퀴즈의 ID
     * @return 해당 퀴즈의 정답률 (0.0 ~ 1.0)
     */
    public double getCorrectRateByQuizId(Long quizId) {
        List<Play> plays = playRepository.findByQuizId(quizId);
        if (plays.isEmpty()) {
            return 0.0;
        }

        long correctCount = plays.stream()
                .filter(Play::isCorrect)
                .count();

        return (double) correctCount / plays.size();
    }
}
