package com.javabom.bomplatform.core.review.repository;

import com.javabom.bomplatform.core.progressmission.model.QProgressMission;
import com.javabom.bomplatform.core.review.model.Review;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.javabom.bomplatform.core.review.model.QReview.review;
import static com.javabom.bomplatform.core.progressmission.model.QProgressMission.progressMission;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findAllByChallengerId(final long challengerId, final Pageable pageable) {
        QueryResults<Review> reviews = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.progressMission, progressMission)
                .where(eqChallengerId(review.progressMission, challengerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(reviews.getResults(), pageable, reviews.getTotal());
    }

    public BooleanExpression eqChallengerId(final QProgressMission progressMission, final long challengerId) {
        return progressMission.challenger.id.eq(challengerId);
    }

}
