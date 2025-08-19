package com.my.quiz.dto;

import com.my.quiz.entity.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {
    private Long no;
    @NotBlank(message = "아이디는 반드시 입력하셔야 합니다.")
    private String id;
    private String password;
    private Boolean status = false;
    private int answerTrue = 0;
    private int answerFalse = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static MemberDto fromMemberEntity(Member member) {
        return new MemberDto(
                member.getNo(),
                member.getId(),
                member.getPassword(),
                member.getStatus(),
                member.getAnswerTrue(),
                member.getAnswerFalse(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static Member fromMemberDto(MemberDto dto) {
        Member member = new Member();
        member.setNo(dto.getNo());
        member.setId(dto.getId());
        member.setPassword(dto.getPassword());
        member.setStatus(dto.getStatus());
        member.setAnswerTrue(dto.getAnswerTrue());
        member.setAnswerFalse(dto.getAnswerFalse());
        return member;
    }
}
