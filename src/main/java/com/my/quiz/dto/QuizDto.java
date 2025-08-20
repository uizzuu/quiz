package com.my.quiz.dto;

import com.my.quiz.entity.Member;
import com.my.quiz.entity.Quiz;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    @NotBlank(message = "퀴즈 내용은 공백일 수 없습니다.")
    private String content;
    @NotNull(message = "퀴즈 정답 여부를 반드시 입력해야 합니다.")
    private Boolean answer;
    private Long memberNo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 작성자 정보를 담기 위한 필드 추가
    private MemberDto member;

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static QuizDto fromQuizEntity(Quiz quiz) {
        QuizDto dto = new QuizDto();
        dto.setId(quiz.getId());
        dto.setContent(quiz.getContent());
        dto.setAnswer(quiz.getAnswer());

        // 작성자 정보가 있을 경우 DTO에 설정
        if (quiz.getMember() != null) {
            dto.setMemberNo(quiz.getMember().getNo());
            MemberDto memberDto = new MemberDto();
            memberDto.setNo(quiz.getMember().getNo());
            memberDto.setId(quiz.getMember().getId());
            dto.setMember(memberDto);
        }

        dto.setCreatedAt(quiz.getCreatedAt());
        dto.setUpdatedAt(quiz.getUpdatedAt());

        return dto;
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static Quiz fromQuizDto(QuizDto dto, Member member) {
        Quiz quiz = new Quiz();
        quiz.setContent(dto.getContent());
        quiz.setAnswer(dto.getAnswer());
        quiz.setMember(member);
        return quiz;
    }
}
