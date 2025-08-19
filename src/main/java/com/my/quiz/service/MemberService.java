package com.my.quiz.service;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.entity.Member;
import com.my.quiz.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class MemberService {

    // 리포지토리를 생성자 주입 방법으로 가져오기
    private final MemberRepository repository;
    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    // 리포지토리를 통해서 멤버리스트 가져오기
    public List<MemberDto> getAllList() {
        List<Member> memberList = repository.findAll();
        System.out.println(memberList);

        // fromMemberEntity
        return memberList
                .stream()
                .map(x -> MemberDto.fromMemberEntity(x))
                .toList();
    }

    // 멤버 추가
    public void insertMember(MemberDto dto) {
        // ID 중복 체크
        if (repository.existsById(dto.getId())) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        Member member = MemberDto.fromMemberDto(dto);
        member = repository.save(member);
        System.out.println("==============");
        System.out.println(member);
    }

    // 멤버 삭제
    public void deleteMember(Long id) {
        repository.deleteById(id);
    }

    // 멤버 검색 (no로)
    public MemberDto findMember(Long updateId) {
        Member member = repository.findById(updateId).orElse(null);
        if (ObjectUtils.isEmpty(member)) {
            return null;
        } else {
            return MemberDto.fromMemberEntity(member);
        }
    }

    // 멤버 검색 (id로) - 추가된 메소드
    public MemberDto findMemberById(String id) {
        Member member = repository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(member)) {
            return null;
        } else {
            return MemberDto.fromMemberEntity(member);
        }
    }

    // 멤버 수정
    public void updateMember(MemberDto dto) {
        if (dto.getNo() == null) {
            throw new IllegalArgumentException("회원 번호가 필요합니다.");
        }

        // 기존 회원 정보를 가져와서 필요한 필드만 업데이트
        Member existingMember = repository.findById(dto.getNo()).orElse(null);
        if (existingMember == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        // 업데이트할 필드들 설정
        existingMember.setId(dto.getId());
        existingMember.setPassword(dto.getPassword());
        existingMember.setStatus(dto.getStatus());
        existingMember.setAnswerTrue(dto.getAnswerTrue());
        existingMember.setAnswerFalse(dto.getAnswerFalse());

        repository.save(existingMember);
    }

    // 멤버 검색 조건
    public List<MemberDto> searchMember(String type, String keyword) {
        if (ObjectUtils.isEmpty(keyword)) {
            return getAllList();
        }

        switch (type) {
            case "id":
                return repository.searchId(keyword)
                        .stream()
                        .map(x -> MemberDto.fromMemberEntity(x))
                        .toList();
            default:
                return repository.searchQuery()
                        .stream()
                        .map(x -> MemberDto.fromMemberEntity(x))
                        .toList();
        }
    }

    // 멤버 승인
    public void approveMember(Long memberNo) {
        MemberDto dto = findMember(memberNo);
        if (dto != null) {
            dto.setStatus(true);
            updateMember(dto);
        }
    }

    // 통계용 메소드들
    public long getTotalMemberCount() {
        return repository.count();
    }

    public long getApprovedMemberCount() {
        return repository.countByStatus(true);
    }

    // 승인된 회원 목록 조회
    public List<MemberDto> getApprovedMembers() {
        return repository.findByStatusOrderByIdAsc(true)
                .stream()
                .map(MemberDto::fromMemberEntity)
                .toList();
    }

    // 회원 점수 업데이트
    public void updateScore(Long memberNo, boolean isCorrect) {
        Member member = repository.findById(memberNo).orElse(null);
        if (member != null) {
            if (isCorrect) {
                member.setAnswerTrue(member.getAnswerTrue() + 1);
            } else {
                member.setAnswerFalse(member.getAnswerFalse() + 1);
            }
            repository.save(member);
        }
    }
}