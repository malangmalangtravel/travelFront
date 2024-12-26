package com.pr.travel.board.domain;

import com.pr.config.SearchCondition;
import com.pr.travel.board.domain.QTravelArticle;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.pr.member.domain.QMemberInfo.memberInfo;

@RequiredArgsConstructor
@Repository
public class TravelBoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public Page<TravelArticle> findAllBySearchCondition(Pageable pageable, SearchCondition searchCondition) {
        JPAQuery<TravelArticle> query = queryFactory.selectFrom(QTravelArticle.travelArticle)
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()));

        long total = query.stream().count();   //여기서 전체 카운트 후 아래에서 조건작업

        List<TravelArticle> results = query
                .where(searchKeywords(searchCondition.getSk(), searchCondition.getSv()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QTravelArticle.travelArticle.id.desc())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

    private BooleanExpression searchKeywords(String sk, String sv) {
        if("nickName".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return memberInfo.nickName.contains(sv);
            }
        } else if ("title".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return QTravelArticle.travelArticle.title.contains(sv);
            }
        } else if ("content".equals(sk)) {
            if(StringUtils.hasLength(sv)) {
                return QTravelArticle.travelArticle.content.contains(sv);
            }
        }

        return null;
    }
}