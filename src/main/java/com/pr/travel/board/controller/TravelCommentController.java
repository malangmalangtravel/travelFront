package com.pr.travel.board.controller;

import com.pr.travel.board.domain.TravelComment;
import com.pr.travel.board.service.TravelCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/travelComments")
public class TravelCommentController {

    @Autowired
    private TravelCommentService travelCommentService;

    @RequestMapping(value="/article/{articleId}", method= RequestMethod.GET)
    public List<TravelComment> getCommentsByArticleId(@PathVariable Long articleId) {
        return travelCommentService.getCommentsByArticleId(articleId);
    }

    @PostMapping
    public TravelComment addComment(@RequestBody TravelComment travelComment) {
        return travelCommentService.addComment(travelComment);
    }
}