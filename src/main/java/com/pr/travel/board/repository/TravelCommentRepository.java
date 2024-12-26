package com.pr.travel.board.repository;


import com.pr.travel.board.domain.TravelComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TravelCommentRepository extends JpaRepository<TravelComment, Long> {
    List<TravelComment> findByTravelArticle_IdAndDeleteYn(Long articleId, String deleteYn);
}