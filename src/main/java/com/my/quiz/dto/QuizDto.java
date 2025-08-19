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

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static QuizDto fromQuizEntity(Quiz quiz) {
        return new QuizDto(
                quiz.getId(),
                quiz.getContent(),
                quiz.getAnswer(),
                quiz.getMember() != null ? quiz.getMember().getNo() : null,
                quiz.getCreatedAt(),
                quiz.getUpdatedAt()
        );
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
