package com.my.quiz.repository;

import com.my.quiz.dto.MemberDto;
import com.my.quiz.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // no로 검색 (전체 조회)
    @Query(value = "SELECT * FROM member ORDER BY no", nativeQuery = true)
    List<Member> searchQuery();

    // id로 검색 - 수정된 쿼리 (name -> id로 변경)
    @Query(value = "SELECT * FROM member WHERE id LIKE %:keyword% ORDER BY no", nativeQuery = true)
    List<Member> searchId(@Param("keyword") String keyword);

    // id로 멤버 찾기 (문자열 id로 검색)
    Optional<Member> findById(String id);

    // id 중복 확인용
    boolean existsById(String id);

    // 승인된 회원 수 조회
    long countByStatus(boolean status);

    // 승인된 회원 목록 조회
    List<Member> findByStatusOrderByIdAsc(boolean status);

    //
}