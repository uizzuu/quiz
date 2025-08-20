package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.Member;
import com.my.quiz.service.MemberService;
import com.my.quiz.service.PlayService;
import com.my.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private PlayService playService; // PlayService를 주입합니다.

    // 퀴즈 목록 보기
    @GetMapping("/list")
    // 📌[수정] 로그인 체크 로직 추가
    public String quizList(Model model, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

        List<QuizDto> quizList = quizService.getAllQuizzes();
        model.addAttribute("quizList", quizList);
        model.addAttribute("title", "퀴즈 목록");
        return "quizList";
    }

    // QuizController.java 내의 insertForm 메소드
    @GetMapping("insertForm")
    public String insertForm(Model model, HttpSession session) {
        // 세션에서 로그인된 회원 정보를 가져옴
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");

        // 로그인이 안 되어 있다면 로그인 페이지로 리다이렉트 (필요에 따라)
        if (loggedInMember == null) {
            return "redirect:/";
        }

        model.addAttribute("dto", new QuizDto());
        model.addAttribute("loggedInMember", loggedInMember); // 로그인된 회원 정보를 모델에 추가

        // 기존에 있던 approvedMembers 목록을 가져오는 코드는 삭제
        // List<MemberDto> memberList = memberService.getApprovedMembers();
        // model.addAttribute("memberList", memberList);

        return "quizInsertForm";
    }

    // 퀴즈 등록
    @PostMapping("/insert")
    public String insertQuiz(@Valid @ModelAttribute("dto") QuizDto dto,
                             BindingResult bindingResult,
                             HttpSession session,
                             Model model) {

        // 로그인된 회원 정보 가져오기
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            // 로그인 정보가 없으면 로그인 페이지로 리다이렉트
            return "redirect:/";
        }

        // 유효성 검사 실패 시
        if (bindingResult.hasErrors()) {
            model.addAttribute("loggedInMember", loggedInMember); // 로그인된 회원 정보 다시 추가
            return "insertForm";
        }

        // DTO에 작성자(memberNo) 설정
        dto.setMemberNo(loggedInMember.getNo());

        // 퀴즈 저장
        quizService.saveQuiz(dto);

        // 작성자 역할에 따라 다른 페이지로 리다이렉트
        if ("1".equals(loggedInMember.getRole())) {
            // 관리자(role이 '1')는 퀴즈 목록 페이지로 리다이렉트
            return "redirect:/quiz/list";
        } else {
            // 일반 사용자는 메인 페이지로 리다이렉트
            return "redirect:/main";
        }
    }

    // 퀴즈 수정 폼
    @GetMapping("/updateForm/{id}")
    // 📌[수정] 로그인 체크 로직 추가
    public String updateForm(@PathVariable Long id, Model model, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

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
    // 📌[수정] 로그인 체크 로직 추가
    public String updateQuiz(@Valid @ModelAttribute("dto") QuizDto dto,
                             BindingResult bindingResult, Model model, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

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
    // 📌[수정] 로그인 체크 로직 추가
    public String deleteQuiz(@PathVariable Long id, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

        quizService.deleteQuiz(id);
        return "redirect:/quiz/list";
    }

    // 퀴즈 플레이 페이지
    @GetMapping("/play")
    public String playQuiz(Model model, HttpSession session) {
        // 세션에서 로그인된 사용자 정보를 가져옴
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        if (loggedInMember == null) {
            return "redirect:/";
        }

        List<QuizDto> quizList = quizService.getAllQuizzes();
        if (ObjectUtils.isEmpty(quizList)) {
            model.addAttribute("message", "등록된 퀴즈가 없습니다.");
            return "noQuiz";
        }

        // 랜덤하게 퀴즈 하나 선택 (또는 첫 번째 퀴즈)
        QuizDto randomQuiz = quizList.get((int) (Math.random() * quizList.size()));
        model.addAttribute("quiz", randomQuiz);
        model.addAttribute("totalQuizCount", quizList.size());

//        // 수정된 부분: 관리자를 제외한 승인된 회원 목록을 가져옴
//        List<MemberDto> memberList = memberService.getApprovedNonAdminMembers();
//        model.addAttribute("memberList", memberList);

        // 수정: 로그인된 사용자 정보를 모델에 추가
        model.addAttribute("loggedInMember", loggedInMember);

        return "playQuiz";
    }

    // 퀴즈 정답 체크
    @PostMapping("/check")
    // 📌[수정] 로그인 체크 로직 추가
    public String checkAnswer(@RequestParam Long quizId,
                              @RequestParam Boolean userAnswer,
                              @RequestParam Long memberNo,
                              Model model,
                              HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

        QuizDto quiz = quizService.findById(quizId);
        boolean isCorrect = quiz.getAnswer().equals(userAnswer);


        // 플레이 기록을 저장하는 로직을 추가합니다.
        // 수정된 부분: playService.savePlay 메서드에 quizId를 추가로 전달합니다.
        playService.savePlay(quizId, memberNo, isCorrect);

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

    // '등록 퀴즈 보기' 버튼 클릭 시 호출되는 API (JSON 반환)
    @GetMapping("/api/byMember")
    @ResponseBody
    // 📌[수정] 로그인 체크 로직 추가
    public List<QuizDto> getQuizzesByMemberApi(@RequestParam("memberNo") Long memberNo, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return Collections.emptyList(); // 빈 리스트 반환
        }

        try {
            return quizService.findByMemberNo(memberNo);
        } catch (Exception e) {
            System.err.println("Error fetching quizzes for memberNo " + memberNo + ": " + e.getMessage());
            // 예외 발생 시 빈 리스트를 반환하여 프론트엔드가 오류 없이 처리하게 함
            return Collections.emptyList();
        }
    }
}
