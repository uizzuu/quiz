package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
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
        if (session.getAttribute("loggedInMember") != null) {
            return "redirect:/main";
        }
        return "login";
    }

    // 2. 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam("id") String id,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        MemberDto member = memberService.findMemberById(id);
        if (member != null && member.getPassword().equals(password)) {
            if (!member.isStatus()) {
                model.addAttribute("error", "관리자의 승인이 필요한 계정입니다.");
                return "login";
            }
            session.setAttribute("loggedInMember", member);
            return "redirect:/main";
        } else {
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

        // 📌[수정] 통계 데이터를 관리자와 일반 회원 모두에게 전달하기 위해
        // if-else문 밖으로 이동했습니다.
        model.addAttribute("totalQuizzes", quizService.getTotalQuizCount());
        model.addAttribute("totalPlays", playService.getTotalPlayCount());
        model.addAttribute("memberName", loggedInMember.getId());
        model.addAttribute("memberNo", loggedInMember.getNo());

        boolean isAdmin = "1".equals(loggedInMember.getRole());

        if (isAdmin) {
            model.addAttribute("isAdmin", true);
            model.addAttribute("totalMembers", memberService.getTotalMemberCount());
            model.addAttribute("approvedMembers", memberService.getApprovedMemberCount());
            return "index"; // 관리자는 index.html로 이동
        } else {
            model.addAttribute("isAdmin", false);
            return "main"; // 일반 회원은 main.html로 이동
        }
    }

    // 4. 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }


    // 5. 관리자 통계 페이지
    @GetMapping("/admin/stats")
    public String showStats(HttpSession session, Model model) {
        MemberDto loggedInMember = (MemberDto) session.getAttribute("loggedInMember");
        if (loggedInMember == null || !"1".equals(loggedInMember.getRole())) {
            return "redirect:/"; // 관리자만 접근 가능
        }

        // 회원별 통계 데이터 계산 및 모델에 추가
        List<MemberDto> memberList = memberService.getAllList();
        for (MemberDto member : memberList) {
            member.setTotalPlays(memberService.getTotalPlaysByMemberNo(member.getNo()));
            member.setTotalQuizzes(quizService.countQuizzesByMemberNo(member.getNo()));
            member.setCorrectRate(memberService.getCorrectRateByMemberNo(member.getNo()));
        }

        // 퀴즈별 통계 데이터 계산 및 모델에 추가
        List<QuizDto> quizList = quizService.getAllQuizzes();
        for (QuizDto quiz : quizList) {
            // 참고: Play 엔티티에 퀴즈 ID가 없으므로 현재로서는 정확한 정답률 계산이 불가능합니다.
            // 따라서 0.0을 반환하도록 처리했습니다.
            quiz.setCorrectRate(quizService.getCorrectRateByQuizId(quiz.getId()));
        }

        // HTML 템플릿과 변수명 일치를 위해 수정
        model.addAttribute("memberStatsList", memberList);
        model.addAttribute("quizStatsList", quizList);

        return "stats";
    }
}
