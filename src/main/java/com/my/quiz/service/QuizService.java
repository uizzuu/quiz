package com.my.quiz.service;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.Member;
import com.my.quiz.entity.Quiz;
import com.my.quiz.repository.MemberRepository;
import com.my.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository repository;
    private final MemberRepository memberRepository;

    public QuizService(QuizRepository repository, MemberRepository memberRepository) {
        this.repository = repository;
        this.memberRepository = memberRepository;
    }

    // 특정 회원이 작성한 퀴즈 조회
    public List<QuizDto> findByMemberNo(Long memberNo) {
        List<Quiz> quizzes = repository.findByMemberNoOrderByCreatedAtDesc(memberNo);
        return quizzes.stream()
                .map(QuizDto::fromQuizEntity)
                .toList();
    }

    // 모든 퀴즈 조회
    public List<QuizDto> getAllQuizzes() {
        List<Quiz> quizzes = repository.findAll();
        return quizzes.stream()
                .map(QuizDto::fromQuizEntity)
                .toList();
    }

    // 퀴즈 저장
    public void saveQuiz(QuizDto dto) {
        Member member = memberRepository.findById(dto.getMemberNo()).orElse(null);
        if (member != null) {
            Quiz quiz = QuizDto.fromQuizDto(dto, member);
            repository.save(quiz);
        }
    }

    // 퀴즈 ID로 조회
    public QuizDto findById(Long id) {
        Quiz quiz = repository.findById(id).orElse(null);
        return quiz != null ? QuizDto.fromQuizEntity(quiz) : null;
    }

    // 퀴즈 수정
    public void updateQuiz(QuizDto dto) {
        Quiz existingQuiz = repository.findById(dto.getId()).orElse(null);
        if (existingQuiz != null) {
            existingQuiz.setContent(dto.getContent());
            existingQuiz.setAnswer(dto.getAnswer());
            repository.save(existingQuiz);
        }
    }

    // 퀴즈 삭제
    public void deleteQuiz(Long id) {
        repository.deleteById(id);
    }

    // Quiz 엔티티를 반환하는 메서드 추가 (PlayService에서 사용)
    public Quiz getQuizById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // 추가된 메서드: 총 퀴즈 수
    public long getTotalQuizCount() {
        return repository.count();
    }

    // 추가된 메서드: 회원별 등록 퀴즈 수 계산
    public int countQuizzesByMemberNo(long memberNo) {
        // QuizRepository에 countByMemberNo(Long memberNo) 메서드가 있다고 가정합니다.
        return (int) repository.countByMemberNo(memberNo);
    }

    // 추가된 메서드: 퀴즈별 정답률
    // 주의: 현재 Play 엔티티에 퀴즈 ID가 없으므로 정확한 계산이 불가능합니다.
    // 이 메서드는 임시로 0.0을 반환합니다.
    public double getCorrectRateByQuizId(Long quizId) {
        return 0.0;
    }
}
