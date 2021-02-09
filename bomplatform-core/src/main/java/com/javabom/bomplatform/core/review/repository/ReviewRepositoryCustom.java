package com.javabom.bomplatform.core.review.repository;

import com.javabom.bomplatform.core.review.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> findAllByChallengerId(long challengerId, Pageable pageable);

    Page<Review> findAllByReviewerId(String reviewerGithubId, Pageable pageable);
}
