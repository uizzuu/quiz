package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.Member;
import com.my.quiz.service.MemberService;
import com.my.quiz.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private MemberService memberService;

    // 퀴즈 목록 보기
    @GetMapping("/list")
    public String quizList(Model model) {
        List<QuizDto> quizList = quizService.getAllQuizzes();
        model.addAttribute("quizList", quizList);
        model.addAttribute("title", "퀴즈 목록");
        return "quizList";
    }

    // 퀴즈 등록 폼
    @GetMapping("/insertForm")
    public String insertForm(Model model) {
        model.addAttribute("dto", new QuizDto());
        List<MemberDto> memberList = memberService.getApprovedMembers();
        model.addAttribute("memberList", memberList);
        return "quizInsertForm";
    }

    // 퀴즈 등록
    @PostMapping("/insert")
    public String insertQuiz(@Valid @ModelAttribute("dto") QuizDto dto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<MemberDto> memberList = memberService.getApprovedMembers();
            model.addAttribute("memberList", memberList);
            return "quizInsertForm";
        }

        quizService.saveQuiz(dto);
        return "redirect:/quiz/list";
    }

    // 퀴즈 수정 폼
    @GetMapping("/updateForm/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        QuizDto quiz = quizService.findById(id);
        if (quiz == null) {
            return "redirect:/quiz/list";
        }

        model.addAttribute("dto", quiz);
        List<MemberDto> memberList = memberService.getApprovedMembers();
        model.addAttribute("memberList", memberList);
        return "quizUpdateForm";
    }

    // 퀴즈 수정
    @PostMapping("/update")
    public String updateQuiz(@Valid @ModelAttribute("dto") QuizDto dto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<MemberDto> memberList = memberService.getApprovedMembers();
            model.addAttribute("memberList", memberList);
            return "quizUpdateForm";
        }

        quizService.updateQuiz(dto);
        return "redirect:/quiz/list";
    }

    // 퀴즈 삭제
    @PostMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz/list";
    }

    // 퀴즈 플레이 페이지
    @GetMapping("/play")
    public String playQuiz(Model model) {
        List<QuizDto> quizList = quizService.getAllQuizzes();
        if (ObjectUtils.isEmpty(quizList)) {
            model.addAttribute("message", "등록된 퀴즈가 없습니다.");
            return "noQuiz";
        }

        // 랜덤하게 퀴즈 하나 선택 (또는 첫 번째 퀴즈)
        QuizDto randomQuiz = quizList.get((int) (Math.random() * quizList.size()));
        model.addAttribute("quiz", randomQuiz);
        model.addAttribute("totalQuizCount", quizList.size());

        return "playQuiz";
    }

    // 퀴즈 정답 체크
    @PostMapping("/check")
    public String checkAnswer(@RequestParam Long quizId,
                              @RequestParam Boolean userAnswer,
                              @RequestParam Long memberNo,
                              Model model) {

        QuizDto quiz = quizService.findById(quizId);
        boolean isCorrect = quiz.getAnswer().equals(userAnswer);

        model.addAttribute("quiz", quiz);
        model.addAttribute("userAnswer", userAnswer);
        model.addAttribute("isCorrect", isCorrect);
        model.addAttribute("correctAnswer", quiz.getAnswer());

        // 회원 점수 업데이트 (옵션)
        if (memberNo != null && memberNo > 0) {
            memberService.updateScore(memberNo, isCorrect);
        }

        return "quizResult";
    }
}