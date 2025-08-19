package com.my.quiz.service;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.Member;
import com.my.quiz.entity.Quiz;
import com.my.quiz.repository.MemberRepository;
import com.my.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    // 통계용 메소드
    public long getTotalQuizCount() {
        return repository.count();
    }
}