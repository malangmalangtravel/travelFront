package com.pr.travel.board.service;

import com.pr.config.SearchCondition;
import com.pr.humor.board.domain.HumorArticle;
import com.pr.humor.board.dto.HumorBoardDto;
import com.pr.humor.board.model.Header;
import com.pr.humor.board.model.Pagination;
import com.pr.member.domain.MemberInfo;
import com.pr.member.repository.MemberRepository;
import com.pr.travel.board.domain.TravelBoardRepositoryCustom;
import com.pr.travel.board.domain.TravelArticle;
import com.pr.travel.board.dto.TravelBoardDto;
import com.pr.travel.board.repository.TravelBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TravelBoardService {

    private final TravelBoardRepository travelBoardRepository;
    private final TravelBoardRepositoryCustom travelBoardRepositoryCustom;
    private final MemberRepository memberRepository;

    // 기존 메서드
    private Header<List<TravelBoardDto>> getBoardListByBoardCode(Pageable pageable, String boardCode) {
        // 기존 코드와 동일
        Page<TravelArticle> boardPage = travelBoardRepository.findByBoardCodeAndDeleteYnOrderByIdDesc(boardCode, "n", pageable);

        List<TravelBoardDto> dtos = boardPage.stream()
                .map(board -> convertToDto(board, null, null))
                .collect(Collectors.toList());

        Pagination pagination = new Pagination(
                (int) boardPage.getTotalElements(),
                pageable.getPageNumber() + 1,
                pageable.getPageSize(),
                5
        );

        return Header.OK(dtos, pagination);
    }

    // 게시판 리스트 조회
    public Header<List<TravelBoardDto>> getTravelBoardList(
            Pageable pageable,
            SearchCondition searchCondition,
            String boardCode
    ) {
        return getBoardListByBoardCode(pageable, boardCode);
    }

    // 게시글 상세 조회 (동적 처리)
    public TravelBoardDto getBoardDetail(Long id, String boardCode) {
        TravelArticle travelBoard = findBoardById(id);

        // 이전, 다음 게시글 ID 조회
        Long nextBoardId = travelBoardRepository.findNextTravelId(travelBoard.getId(), boardCode).orElse(null);
        Long previousBoardId = travelBoardRepository.findPreviousTravelId(travelBoard.getId(), boardCode).orElse(null);

        return convertToDto(travelBoard, nextBoardId, previousBoardId);
    }

    // 게시글 생성 (동적 처리)
    public TravelBoardDto createBoard(TravelBoardDto travelBoardDto, String boardCode) {
        travelBoardDto.setBoardCode(boardCode);
        TravelArticle travelBoard = convertToEntity(travelBoardDto);
        TravelArticle savedTravelArticle = travelBoardRepository.save(travelBoard);
        return getBoardDetail(savedTravelArticle.getId(), boardCode);
    }

    // 게시글 수정
    public TravelBoardDto updateBoardDetails(TravelBoardDto travelBoardDto) {
        TravelArticle travelBoard = findBoardById(travelBoardDto.getId());
        travelBoard.setTitle(travelBoardDto.getTitle());
        travelBoard.setContent(travelBoardDto.getContent());
        travelBoardRepository.save(travelBoard);
        return convertToDto(travelBoard, null, null);
    }

    // 게시글 삭제
    public void deleteBoardById(Long id) {
        TravelArticle travelBoard = findBoardById(id);
        travelBoard.setDeleteYn("y");
        travelBoardRepository.save(travelBoard);
    }

    // 조회수 증가
    public void incrementViewCount(Long id) {
        travelBoardRepository.incrementViewCount(id);
    }

    // 게시글 존재 여부 확인
    private TravelArticle findBoardById(Long id) {
        return travelBoardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
    }

    // Entity -> DTO 변환 메서드
    private TravelBoardDto convertToDto(TravelArticle travelBoard, Long nextBoardId, Long previousBoardId) {
        return TravelBoardDto.builder()
                .id(travelBoard.getId())
                .nickName(travelBoard.getMemberInfo().getNickName())
                .email(travelBoard.getEmail())
                .title(travelBoard.getTitle())
                .content(travelBoard.getContent())
                .createDate(travelBoard.getCreateDate())
                .viewCount(travelBoard.getViewCount())
                .nextBoardId(nextBoardId)
                .previousBoardId(previousBoardId)
                .boardCode(travelBoard.getBoardCode())
                .build();
    }

    // DTO -> Entity 변환 메서드
    private TravelArticle convertToEntity(TravelBoardDto travelBoardDto) {
        MemberInfo memberInfo = memberRepository.findByEmail(travelBoardDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with email: " + travelBoardDto.getEmail()));

        return TravelArticle.builder()
                .memberInfo(memberInfo)
                .email(travelBoardDto.getEmail())
                .title(travelBoardDto.getTitle())
                .content(travelBoardDto.getContent())
                .createDate(travelBoardDto.getCreateDate()) // 생성일 클라이언트 전달 값
                .viewCount(travelBoardDto.getViewCount())
                .boardCode(travelBoardDto.getBoardCode())
                .build();
    }
}
