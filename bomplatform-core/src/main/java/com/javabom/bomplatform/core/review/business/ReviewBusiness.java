package com.javabom.bomplatform.core.review.business;

import com.javabom.bomplatform.core.review.repository.ReviewRepository;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewBusiness {

    private final ReviewRepository reviewRepository;

    public Long requestReview(ProgressMission progressMission, String reviewURL) {
        final Review review = progressMission.createReview(reviewURL);
        progressMission.startReview();

        reviewRepository.save(review);

        return review.getId();
    }

    public List<Review> findChallengerReviews(final String githubId, final Pageable pageable) {
        return reviewRepository.findAllByChallengerId(githubId, pageable)
                .getContent();
    }

    public List<Review> findReviewerReviews(final String githubId, final Pageable pageable) {
        return reviewRepository.findAllByReviewerId(githubId, pageable)
                .getContent();
    }

    public void completeReview(long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 리뷰입니다."));

        if (review.isComplete()) {
            throw new IllegalStateException("이미 완료된 리뷰입니다.");
        }

        review.complete();
    }
}
