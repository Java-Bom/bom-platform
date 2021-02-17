package com.javabom.bomplatform.core.review.business;

import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.review.model.Review;
import com.javabom.bomplatform.core.review.model.ReviewState;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @DisplayName("현재 review를 완료 상태로 변경한다.")
    @Test
    void completeReviewTest() {
        Review review = new Review(null, "reviewUrl");

        ReviewRepository mockReviewRepository = mock(ReviewRepository.class);

        when(mockReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        ReviewBusiness reviewBusiness = new ReviewBusiness(mockReviewRepository);

        reviewBusiness.completeReview(1L);

        assertThat(review.getReviewState()).isEqualTo(ReviewState.COMPLETE);
    }

    @DisplayName("없는 리뷰 상태를 변경하려하면 IllegalStateException throw")
    @Test
    void completeReviewThrowExceptionTest() {
        ReviewRepository mockReviewRepository = mock(ReviewRepository.class);

        when(mockReviewRepository.findById(1L))
                .thenReturn(Optional.empty());

        ReviewBusiness reviewBusiness = new ReviewBusiness(mockReviewRepository);

        assertThatThrownBy(() -> reviewBusiness.completeReview(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("존재하지 않는 리뷰입니다.");
    }

    @DisplayName("이미 완료된 리뷰의 상태를 Complete로 변경하려하면 IllegalStateException throw")
    @Test
    void completedReviewThrowExceptionTest() {
        ReviewRepository mockReviewRepository = mock(ReviewRepository.class);

        Review review = new Review(null, "reviewUrl");

        review.complete();

        when(mockReviewRepository.findById(1L))
                .thenReturn(Optional.of(review));

        ReviewBusiness reviewBusiness = new ReviewBusiness(mockReviewRepository);

        assertThatThrownBy(() -> reviewBusiness.completeReview(1L))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("이미 완료된 리뷰입니다.");
    }

    private List<Review> createReviews() {
        ProgressMission progressMission = ProgressMission.builder()
                .challenger(new User("challenger@gmail.com", "challenger", UserRole.CHALLENGER))
                .missionReviewers(Arrays.asList(new MissionReviewer("reviewer@gmail.com", "reviewer")))
                .build();
        return Arrays.asList(progressMission.createReview("url1"), progressMission.createReview("url2"));
    }
}