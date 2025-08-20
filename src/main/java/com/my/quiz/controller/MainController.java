package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.PlayDto;
import com.my.quiz.dto.QuizDto;
import com.my.quiz.service.MemberService;
import com.my.quiz.service.PlayService;
import com.my.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private PlayService playService;


    // 1. 로그인 페이지
    @GetMapping("/")
    public String loginPage(HttpSession session) {
        // 이미 로그인된 사용자가 있으면 메인 페이지로 리다이렉트
        if (session.getAttribute("loggedInMember") != null) {
            return "redirect:/main";
        }
        return "login";
    }

 // 2. 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("id") String id,  // username -> id로 변경
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {

        // 아이디로 회원 정보를 조회
        MemberDto member = memberService.findMemberById(id);

        // 회원 정보가 존재하고 비밀번호가 일치하는지 확인
        if (member != null && member.getPassword().equals(password)) {
            // 로그인 성공: 세션에 사용자 정보 저장
            session.setAttribute("loggedInMember", member);
            return "redirect:/main";
        } else {
            // 로그인 실패: 에러 메시지를 모델에 담아 로그인 페이지로 다시 이동
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "login";
        }
    }

    // 3. 로그인 완료 후 메인 화면
    @GetMapping("/main")
    public String main(Model model, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

        // DTO의 role 값을 확인하여 관리자 여부 판단
        boolean isAdmin = "1".equals(loggedInMember.getRole());

        // 통계 데이터를 저장할 변수 초기화
        long totalPlays;

        // 관리자인 경우 관리자 화면으로
        if (isAdmin) {
            long totalMembers = memberService.getTotalMemberCount();
            long totalQuizzes = quizService.getTotalQuizCount();
            long approvedMembers = memberService.getApprovedMemberCount();
            totalPlays = memberService.getTotalPlays(loggedInMember.getNo(), loggedInMember.getRole());
            List<String> recentActivities = getRecentActivities();

            model.addAttribute("totalMembers", totalMembers);
            model.addAttribute("totalQuizzes", totalQuizzes);
            model.addAttribute("approvedMembers", approvedMembers);
            model.addAttribute("recentActivities", recentActivities);
            model.addAttribute("isAdmin", true);
            model.addAttribute("totalPlays", totalPlays); // totalPlays 추가

            return "index"; // 관리자용 화면
        } else {
            // 일반 사용자인 경우
            totalPlays = memberService.getTotalPlays(loggedInMember.getNo(), loggedInMember.getRole());

            model.addAttribute("isAdmin", false);
            model.addAttribute("totalQuizzes", quizService.getTotalQuizCount());
            model.addAttribute("totalPlays", totalPlays); // totalPlays 추가
            model.addAttribute("memberName", loggedInMember.getId());

            return "main"; // 일반 사용자용 화면
        }
    }

    // 4. 나의 퀴즈 기록 페이지
    @GetMapping("/member/myRecord")
    public String myRecord(Model model, HttpSession session) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null) {
            return "redirect:/";
        }

        // 로그인된 회원의 퀴즈 플레이 기록과 작성한 퀴즈 목록을 가져옴
        List<PlayDto> plays = playService.findByMemberNo(loggedInMember.getNo());
        List<QuizDto> quizzes = quizService.findByMemberNo(loggedInMember.getNo());

        // 모델에 데이터 추가
        model.addAttribute("plays", plays);
        model.addAttribute("quizzes", quizzes);
        model.addAttribute("member", loggedInMember); // 회원 정보도 같이 전달

        return "myRecord";
    }

    // 5. 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 기존의 getRecentActivities() 메소드 (변동 없음)
    private List<String> getRecentActivities() {
        List<String> activities = new ArrayList<>();
        activities.add("새로운 회원이 가입했습니다.");
        activities.add("퀴즈 5개가 새로 등록되었습니다.");
        activities.add("오늘 총 12번의 퀴즈가 플레이되었습니다.");
        activities.add("회원 승인 요청 3건이 대기 중입니다.");
        return activities;
    }


//    @GetMapping("/")
//    public String main(Model model) {
//        try {
//            // 통계 데이터 조회
//            long totalMembers = memberService.getTotalMemberCount();
//            long totalQuizzes = quizService.getTotalQuizCount();
//            long totalPlays = playService.getTotalPlayCount();
//            long approvedMembers = memberService.getApprovedMemberCount();
//
//            // 최근 활동 조회 (간단한 예시)
//            List<String> recentActivities = getRecentActivities();
//
//            model.addAttribute("totalMembers", totalMembers);
//            model.addAttribute("totalQuizzes", totalQuizzes);
//            model.addAttribute("totalPlays", totalPlays);
//            model.addAttribute("approvedMembers", approvedMembers);
//            model.addAttribute("recentActivities", recentActivities);
//
//        } catch (Exception e) {
//            // 데이터 조회 실패 시 기본값 설정
//            model.addAttribute("totalMembers", 0);
//            model.addAttribute("totalQuizzes", 0);
//            model.addAttribute("totalPlays", 0);
//            model.addAttribute("approvedMembers", 0);
//            model.addAttribute("recentActivities", new ArrayList<>());
//        }
//
//        return "index";
//    }
//
//    // 최근 활동을 가져오는 메소드 (예시)
//    private List<String> getRecentActivities() {
//        List<String> activities = new ArrayList<>();
//        activities.add("새로운 회원이 가입했습니다.");
//        activities.add("퀴즈 5개가 새로 등록되었습니다.");
//        activities.add("오늘 총 12번의 퀴즈가 플레이되었습니다.");
//        activities.add("회원 승인 요청 3건이 대기 중입니다.");
//        return activities;
//    }
}