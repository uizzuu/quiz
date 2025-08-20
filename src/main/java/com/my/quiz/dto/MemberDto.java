package com.my.quiz.dto;

import com.my.quiz.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long no;
    private String id;
    private String password;
    private String role;
    private boolean status;
    private int answerTrue;
    private int answerFalse;
    private LocalDateTime createdAt;  // boolean에서 LocalDateTime으로 변경
    private LocalDateTime updatedAt;  // boolean에서 LocalDateTime으로 변경

    // 통계 데이터를 담기 위해 추가된 필드들
    private long totalPlays;
    private long totalQuizzes;
    private double correctRate;

    public String getFormattedCreatedAt() {
        if (this.createdAt != null) {
            return this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "정보 없음";
    }

    public String getFormattedUpdatedAt() {
        if (this.updatedAt != null) {
            return this.updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        }
        return "정보 없음";
    }

    /**
     * 엔티티(Member)를 DTO(MemberDto)로 변환하는 함수입니다.
     * Builder 패턴을 사용하여 안정적으로 필드를 매핑합니다.
     * @param member 변환할 Member 엔티티
     * @return 변환된 MemberDto 객체
     */
    public static MemberDto fromMemberEntity(Member member) {
        if (member == null) {
            return null;
        }
        return MemberDto.builder()
                .no(member.getNo())
                .id(member.getId())
                .password(member.getPassword())
                .role(member.getRole())
                .status(member.getStatus())
                .answerTrue(member.getAnswerTrue())
                .answerFalse(member.getAnswerFalse())
                .createdAt(member.getCreatedAt())  // 추가
                .updatedAt(member.getUpdatedAt())  // 추가
                .build();
    }

    /**
     * DTO(MemberDto)를 엔티티(Member)로 변환하는 함수입니다.
     * Builder 패턴을 사용하여 안정적으로 필드를 매핑합니다.
     * @param dto 변환할 MemberDto 객체
     * @return 변환된 Member 엔티티
     */
    public static Member fromMemberDto(MemberDto dto) {
        if (dto == null) {
            return null;
        }
        return Member.builder()
                .no(dto.getNo())
                .id(dto.getId())
                .password(dto.getPassword())
                .role(dto.getRole())
                .status(dto.isStatus())
                .answerTrue(dto.getAnswerTrue())
                .answerFalse(dto.getAnswerFalse())
                .createdAt(dto.getCreatedAt())  // 추가
                .updatedAt(dto.getUpdatedAt())  // 추가
                .build();
    }
}