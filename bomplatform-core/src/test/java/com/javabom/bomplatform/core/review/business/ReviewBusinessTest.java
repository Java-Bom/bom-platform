package com.javabom.bomplatform.core.review.business;

import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.review.model.Review;
import com.javabom.bomplatform.core.review.repository.ReviewRepository;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ReviewBusinessTest {

    @DisplayName("Challenger id로 Review 목록을 요청한다.")
    @Test
    void findChallengerReviewsTest() {
        ReviewRepository mockReviewRepository = mock(ReviewRepository.class);
        Pageable pageable = PageRequest.of(0, 10);
        when(mockReviewRepository.findAllByChallengerId("challenger", pageable))
                .thenReturn(new PageImpl<>(createReviews()));

        ReviewBusiness reviewBusiness = new ReviewBusiness(mockReviewRepository);

        List<Review> result = reviewBusiness.findChallengerReviews("challenger", pageable);

        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getReviewURL()).isEqualTo("url1")
        );
    }

    @DisplayName("Reviewer id로 Review 목록을 요청한다.")
    @Test
    void findReviewerReviewsTest() {
        ReviewRepository mockReviewRepository = mock(ReviewRepository.class);
        Pageable pageable = PageRequest.of(0, 10);
        when(mockReviewRepository.findAllByReviewerId("reviewer", pageable))
                .thenReturn(new PageImpl<>(createReviews()));

        ReviewBusiness reviewBusiness = new ReviewBusiness(mockReviewRepository);

        List<Review> result = reviewBusiness.findReviewerReviews("reviewer", pageable);

        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result.get(0).getReviewURL()).isEqualTo("url1")
        );
    }

    private List<Review> createReviews() {
        ProgressMission progressMission = ProgressMission.builder()
                .challenger(new User("challenger@gmail.com", "challenger", UserRole.CHALLENGER))
                .missionReviewers(Arrays.asList(new MissionReviewer("reviewer@gmail.com", "reviewer")))
                .build();
        return Arrays.asList(progressMission.createReview("url1"), progressMission.createReview("url2"));
    }
}