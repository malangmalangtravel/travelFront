package com.pr.travel.board.repository;

import com.pr.travel.board.domain.TravelArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TravelBoardRepository extends JpaRepository<TravelArticle, Long> {

    // 기본 메서드는 JpaRepository에서 제공
    Optional<TravelArticle> findById(Long id);

    // boardCode와 deleteYn 조건으로 게시글 조회 (페이징)
    Page<TravelArticle> findByBoardCodeAndDeleteYnOrderByIdDesc(String boardCode, String deleteYn, Pageable pageable);

    // 이전 게시글 ID 조회
    @Query("""
        SELECT MAX(h.id) 
        FROM TravelArticle h 
        WHERE h.id < :id 
          AND h.boardCode = :boardCode 
          AND h.deleteYn = 'n'
    """)
    Optional<Long> findPreviousTravelId(@Param("id") Long id, @Param("boardCode") String boardCode);

    // 다음 게시글 ID 조회
    @Query("""
        SELECT MIN(h.id) 
        FROM TravelArticle h 
        WHERE h.id > :id 
          AND h.boardCode = :boardCode 
          AND h.deleteYn = 'n'
    """)
    Optional<Long> findNextTravelId(@Param("id") Long id, @Param("boardCode") String boardCode);

    // 조회수 증가
    @Modifying
    @Query("UPDATE TravelArticle h SET h.viewCount = h.viewCount + 1 WHERE h.id = :id")
    void incrementViewCount(@Param("id") Long id);
}
