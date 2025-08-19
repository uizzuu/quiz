package com.my.quiz.controller;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.dto.PlayDto;
import com.my.quiz.dto.QuizDto;
import com.my.quiz.service.MemberService;
import com.my.quiz.service.PlayService;
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
public class MemberController {
    @Autowired
    MemberService service;

    @Autowired
    PlayService playService;

    @Autowired
    QuizService quizService;

    @GetMapping("/list")
    public String showMember(Model model) {
        model.addAttribute("title", "Member");
        List<MemberDto> memberList = service.getAllList();
        model.addAttribute("list", memberList);
        return "showMember";
    }

    @GetMapping("/member/insertForm")
    public String insertFormView(Model model) {
        // insertForm에 빈 DTO 보냄
        model.addAttribute("dto", new MemberDto());
        return "insertForm";
    }

    @PostMapping("/member/insert")
    // Validation 체크 수행
    public String insert(@Valid @ModelAttribute("dto") MemberDto dto,
                         BindingResult bindingResult) {
        // 0. DTO에서 설정한 Validation에 오류가 있는지 검사
        // 만약, 오류가 있다면 insertForm을 다시 보여준 후 종료
        if (bindingResult.hasErrors()) {
            return "insertForm";
        }

        // 1. 폼에서 보낸 정보를 DTO로 받는다
        System.out.println(dto);
        // 2. 받은 DTO를 서비스로 보낸다
        service.insertMember(dto);
        // 3. 서비스에서 DTO를 엔티티로 바꾼다
        // 4. 리포지토리를 이용해서 저장한다
        // 5. 메인 리스트화면으로 돌아간다
        return "redirect:/list";
    }

    @GetMapping("/member/updateView")
    public String updateView(
            @RequestParam("updateId") String updateId,
            Model model) {
        // 1. 받은 수정 아이디로 데이터를 검색해온다(DTO)
        MemberDto dto = service.findMemberById(updateId);
        // 2. DTO가 비어있는지 확인한다. ID의 유무를 확인 -> 조치
        if (ObjectUtils.isEmpty(dto)) {
            return "redirect:/list";
        } else {
            // 3. 받은 DTO를 수정폼으로 보낸다
            model.addAttribute("dto", dto);
            return "updateForm";
        }
    }

    @PostMapping("/member/update")
    public String update(@Valid @ModelAttribute("dto") MemberDto dto,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "updateForm";
        }
        service.updateMember(dto);
        return "redirect:/list";
    }

    // 승인 처리 - 매개변수 이름 수정
    @PostMapping("/member/approve")
    public String approveMember(@RequestParam("updateId") Long memberNo) {
        service.approveMember(memberNo);
        return "redirect:/list";
    }

    // 상세보기 처리
    @GetMapping("/member/info")
    public String memberInfo(@RequestParam("updateId") Long memberNo, Model model) {
        // 1. 회원 정보 조회
        MemberDto member = service.findMember(memberNo);
        if (member == null) {
            return "redirect:/list";
        }

        // 2. 회원의 플레이 기록 조회
        List<PlayDto> plays = playService.findByMemberNo(memberNo);

        // 3. 회원이 작성한 퀴즈 조회
        List<QuizDto> quizzes = quizService.findByMemberNo(memberNo);

        model.addAttribute("member", member);
        model.addAttribute("plays", plays);
        model.addAttribute("quizzes", quizzes);

        return "memberInfo"; // memberInfo.html 페이지로 이동
    }

    @GetMapping("/member/search")
    public String search(@RequestParam("type") String type,
                         @RequestParam("keyword") String keyword,
                         Model model) {
        List<MemberDto> searchList = service.searchMember(type, keyword);
        if (ObjectUtils.isEmpty(searchList)) {
            // 검색 결과가 없을 경우
            searchList = null;
            model.addAttribute("list", searchList);
        } else {
            // 검색 결과가 있을 경우
            model.addAttribute("list", searchList);
        }
        model.addAttribute("title", "Member 검색결과");
        return "showMember";
    }

    // 삭제 처리 추가
    @PostMapping("/member/delete/{id}")
    public String deleteMember(@PathVariable("id") Long id) {
        service.deleteMember(id);
        return "redirect:/list";
    }
}