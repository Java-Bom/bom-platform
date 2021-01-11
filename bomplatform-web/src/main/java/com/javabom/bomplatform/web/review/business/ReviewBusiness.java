package com.javabom.bomplatform.web.review.business;

import com.javabom.bomplatform.progressmission.model.ProgressMission;
import com.javabom.bomplatform.review.model.Review;
import com.javabom.bomplatform.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
