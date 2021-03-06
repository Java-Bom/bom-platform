package com.javabom.bomplatform.core.review.repository;

import com.javabom.bomplatform.core.progressmission.model.QProgressMission;
import com.javabom.bomplatform.core.review.model.Review;
import com.javabom.bomplatform.core.user.model.QUser;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import static com.javabom.bomplatform.core.progressmission.model.QMissionReviewer.missionReviewer;
import static com.javabom.bomplatform.core.review.model.QReview.review;
import static com.javabom.bomplatform.core.progressmission.model.QProgressMission.progressMission;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Review> findAllByChallengerId(final String challengerId, final Pageable pageable) {
        QueryResults<Review> reviews = jpaQueryFactory.selectFrom(review)
                .leftJoin(review.progressMission, progressMission)
                .where(eqChallengerId(progressMission.challenger, challengerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(reviews.getResults(), pageable, reviews.getTotal());
    }

    @Override
    public Page<Review> findAllByReviewerId(final String reviewerId, final Pageable pageable) {
        QueryResults<Review> reviews = jpaQueryFactory.selectFrom(review)
                .join(review.progressMission, progressMission)
                .join(progressMission.missionReviewers, missionReviewer)
                .where(missionReviewer.githubId.eq(reviewerId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(reviews.getResults(), pageable, reviews.getTotal());
    }

    public BooleanExpression eqChallengerId(final QUser challenger, final String challengerId) {
        return challenger.githubId.eq(challengerId);
    }
}
