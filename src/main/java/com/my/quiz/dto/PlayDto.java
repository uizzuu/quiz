package com.my.quiz.dto;

import com.my.quiz.entity.Member;
import com.my.quiz.entity.Play;
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
public class PlayDto {
    private Long id;
    private Long memberNo;
    private int yes = 0;
    private int no = 0;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 엔티티를 받아서 Dto로 변환해 주는 함수
    public static PlayDto fromPlayEntity(Play play) {
        return new PlayDto(
                play.getId(),
                play.getMember() != null ? play.getMember().getNo() : null,
                play.getYes(),
                play.getNo(),
                play.getCreatedAt(),
                play.getUpdatedAt()
        );
    }

    // DTO를 받아서 Entity에 넣는 작업
    public static Play fromPlayDto(PlayDto dto, Member member) {
        Play play = new Play();
        play.setMember(member);
        play.setYes(dto.getYes());
        play.setNo(dto.getNo());
        return play;
    }
}
