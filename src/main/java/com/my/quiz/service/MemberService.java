package com.my.quiz.service;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.entity.Member;
import com.my.quiz.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.repository = repository;
    }

    // 모든 멤버 목록을 DTO 형태로 가져오는 메서드 추가
    public List<MemberDto> getAllMembers() {
        return repository.findAll().stream()
                .map(MemberDto::fromMemberEntity)
                .collect(Collectors.toList());
    }

    public List<MemberDto> getAllList() {
        List<Member> memberList = repository.findAll();
        System.out.println(memberList);
        return memberList
                .stream()
                .map(x -> MemberDto.fromMemberEntity(x))
                .toList();
    }

    // 멤버 추가
    public void insertMember(MemberDto dto) {
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

    // 멤버 검색 (id로)
    public MemberDto findMemberById(String id) {
        Member member = repository.findById(id).orElse(null);
        if (ObjectUtils.isEmpty(member)) {
            return null;
        } else {
            return MemberDto.fromMemberEntity(member);
        }
    }

    // Member 엔티티를 반환하는 메서드 (통계 계산에 사용)
    public Member getMemberByNo(Long no) {
        return repository.findById(no).orElse(null);
    }

    // 멤버 수정
    public void updateMember(MemberDto dto) {
        if (dto.getNo() == null) {
            throw new IllegalArgumentException("회원 번호가 필요합니다.");
        }
        Member existingMember = repository.findById(dto.getNo()).orElse(null);
        if (existingMember == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        existingMember.setId(dto.getId());
        existingMember.setPassword(dto.getPassword());
        existingMember.setStatus(dto.isStatus());
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

    public List<MemberDto> getApprovedNonAdminMembers() {
        return getApprovedMembers().stream()
                .filter(member -> !"1".equals(member.getRole()))
                .collect(Collectors.toList());
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

    public MemberDto findMemberByUsername(String username) {
        Member member = repository.findById(username).orElse(null);
        if (member != null) {
            return MemberDto.fromMemberEntity(member);
        }
        return null;
    }

    public long getTotalPlays(Long memberNo, String role) {
        if ("1".equals(role)) {
            return repository.findAll().stream()
                    .mapToLong(member -> (long) member.getAnswerTrue() + member.getAnswerFalse())
                    .sum();
        } else {
            return repository.findById(memberNo)
                    .map(member -> (long) member.getAnswerTrue() + member.getAnswerFalse())
                    .orElse(0L);
        }
    }

    // 추가된 메서드: 회원별 총 플레이 횟수 계산
    public int getTotalPlaysByMemberNo(Long memberNo) {
        Member member = repository.findByNo(memberNo).orElse(null);
        if (member != null) {
            return member.getAnswerTrue() + member.getAnswerFalse();
        }
        return 0;
    }

    // 추가된 메서드: 회원별 정답률 계산
    public double getCorrectRateByMemberNo(Long memberNo) {
        Member member = repository.findByNo(memberNo).orElse(null);
        if (member != null) {
            double totalPlays = member.getAnswerTrue() + member.getAnswerFalse();
            if (totalPlays > 0) {
                return (member.getAnswerTrue() / totalPlays) * 100.0;
            }
        }
        return 0.0;
    }
}
