package com.my.quiz.controller;

import com.my.quiz.service.MemberService;
import com.my.quiz.service.PlayService;
import com.my.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/")
    public String main(Model model) {
        try {
            // 통계 데이터 조회
            long totalMembers = memberService.getTotalMemberCount();
            long totalQuizzes = quizService.getTotalQuizCount();
            long totalPlays = playService.getTotalPlayCount();
            long approvedMembers = memberService.getApprovedMemberCount();

            // 최근 활동 조회 (간단한 예시)
            List<String> recentActivities = getRecentActivities();

            model.addAttribute("totalMembers", totalMembers);
            model.addAttribute("totalQuizzes", totalQuizzes);
            model.addAttribute("totalPlays", totalPlays);
            model.addAttribute("approvedMembers", approvedMembers);
            model.addAttribute("recentActivities", recentActivities);

        } catch (Exception e) {
            // 데이터 조회 실패 시 기본값 설정
            model.addAttribute("totalMembers", 0);
            model.addAttribute("totalQuizzes", 0);
            model.addAttribute("totalPlays", 0);
            model.addAttribute("approvedMembers", 0);
            model.addAttribute("recentActivities", new ArrayList<>());
        }

        return "index";
    }

    // 최근 활동을 가져오는 메소드 (예시)
    private List<String> getRecentActivities() {
        List<String> activities = new ArrayList<>();
        activities.add("새로운 회원이 가입했습니다.");
        activities.add("퀴즈 5개가 새로 등록되었습니다.");
        activities.add("오늘 총 12번의 퀴즈가 플레이되었습니다.");
        activities.add("회원 승인 요청 3건이 대기 중입니다.");
        return activities;
    }
}