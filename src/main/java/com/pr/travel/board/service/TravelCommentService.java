package com.pr.travel.board.service;

import com.pr.member.domain.MemberInfo;
import com.pr.member.repository.MemberRepository;
import com.pr.travel.board.domain.TravelComment;
import com.pr.travel.board.domain.TravelArticle;
import com.pr.travel.board.repository.TravelBoardRepository;
import com.pr.travel.board.repository.TravelCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TravelCommentService {

    @Autowired
    private TravelCommentRepository travelCommentRepository;

    @Autowired
    private TravelBoardRepository travelBoardRepository;

    @Autowired
    private MemberRepository memberRepository;

    public List<TravelComment> getCommentsByArticleId(Long articleId) {
        List<TravelComment> travelComments = travelCommentRepository.findByTravelArticle_IdAndDeleteYn(articleId, "N");
        for (TravelComment travelComment : travelComments) {
            MemberInfo memberInfo = memberRepository.findByEmail(travelComment.getEmail())
                    .orElse(null);
            if (memberInfo != null) {
                travelComment.setNickName(memberInfo.getNickName());
            }
        }
        return travelComments;
    }

    public TravelComment addComment(TravelComment travelComment) {
        TravelArticle travelArticle = travelBoardRepository.findById(travelComment.getTravelArticle().getId())
                .orElseThrow(() -> new NoSuchElementException("Article with ID " + travelComment.getTravelArticle().getId() + " not found"));
        travelComment.setTravelArticle(travelArticle);
        return travelCommentRepository.save(travelComment);
    }
}