package com.pr.travel.board.controller;

import com.pr.config.SearchCondition;
import com.pr.humor.board.dto.HumorBoardDto;
import com.pr.humor.board.model.Header;
import com.pr.travel.board.dto.TravelBoardDto;
import com.pr.travel.board.service.TravelBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/travel")
@RequiredArgsConstructor
@CrossOrigin
public class TravelBoardController {

    private final TravelBoardService travelBoardService;

    // 게시판 리스트 조회
    @GetMapping("/{boardCode:travel|free|question}Board/list")
    public Header<List<TravelBoardDto>> travelBoardList(
            @PathVariable("boardCode") String boardCode,
            @PageableDefault(sort = {"id"}) Pageable pageable,
            SearchCondition searchCondition
    ) {
        return getBoardList(pageable, searchCondition, boardCode);
    }

    private Header<List<TravelBoardDto>> getBoardList(
            @PageableDefault(sort = {"id"}) Pageable pageable,
            SearchCondition searchCondition,
            String boardCode
    ) {
        // 프론트에서 받은 boardCode 그대로 처리
        return travelBoardService.getTravelBoardList(pageable, searchCondition, boardCode);
    }

    // 게시판 상세 조회
    @GetMapping("/{boardCode}Board/detail/{id}")
    public TravelBoardDto getBoardDetail(
            @PathVariable("boardCode") String boardCode,
            @PathVariable("id") Long id) {
        return travelBoardService.getBoardDetail(id, boardCode);
    }

    // 게시판 게시글 생성
    @PostMapping("/{boardCode}Board/create")
    public TravelBoardDto createBoard(
            @PathVariable("boardCode") String boardCode,
            @RequestBody TravelBoardDto travelBoardDto) {
        return travelBoardService.createBoard(travelBoardDto, boardCode);
    }

    // 게시판 게시글 수정
    @PostMapping("/{boardCode}Board/update")
    public TravelBoardDto updateBoard(
            @PathVariable("boardCode") String boardCode,
            @RequestBody TravelBoardDto travelBoardDto) {
        return travelBoardService.updateBoardDetails(travelBoardDto);
    }

    // 게시판 게시글 삭제
    @PostMapping("/{boardCode}Board/delete/{id}")
    public void deleteBoard(
            @PathVariable("boardCode") String boardCode,
            @PathVariable Long id) {
        travelBoardService.deleteBoardById(id);
    }

    // 게시판 조회수 증가
    @PostMapping("/{boardCode}Board/incrementViewCount/{id}")
    public void incrementViewCount(
            @PathVariable("boardCode") String boardCode,
            @PathVariable("id") Long id) {
        travelBoardService.incrementViewCount(id);
    }
}
