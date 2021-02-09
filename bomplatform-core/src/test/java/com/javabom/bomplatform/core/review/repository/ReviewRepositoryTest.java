package com.javabom.bomplatform.core.review.repository;

import com.javabom.bomplatform.core.config.QueryDslConfiguration;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.progressmission.model.ProgressMissionState;
import com.javabom.bomplatform.core.progressmission.repository.ProgressMissionRepository;
import com.javabom.bomplatform.core.review.model.Review;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@Import(QueryDslConfiguration.class)
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProgressMissionRepository progressMissionRepository;

    @Autowired
    private UserRepository userRepository;

    private User challenger;
    private User reviewer;
    private User watcher;

    @BeforeEach
    void setUp() {

        saveUsers();

        ProgressMission progressMission = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.OPEN)
                .challenger(challenger)
                .missionReviewers(Arrays.asList(toMissionReviewer(reviewer)))
                .build();

        ProgressMission progressMission1 = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.OPEN)
                .challenger(reviewer)
                .missionReviewers(Arrays.asList(toMissionReviewer(challenger)))
                .build();

        progressMissionRepository.save(progressMission);
        reviewRepository.save(progressMission.createReview("url1"));
        reviewRepository.save(progressMission.createReview("url2"));

        progressMissionRepository.save(progressMission1);
        reviewRepository.save(progressMission1.createReview("subUrl"));
    }

    @DisplayName("Challenger id로 Review 조회")
    @Test
    void findReviewByChallengerId() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Review> reviews = reviewRepository.findAllByChallengerId(challenger.getId(), pageRequest);

        assertThat(reviews.getTotalElements()).isEqualTo(2);
    }

    @DisplayName("자신의 리뷰가 없으면 0개가 조회된다.")
    @Test
    void noIdWillReturnSizeZeroPageable() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Review> reviews = reviewRepository.findAllByChallengerId(watcher.getId(), pageRequest);

        assertThat(reviews.getTotalElements()).isEqualTo(0);
    }

    @DisplayName("Reviewer id로 Review 조회")
    @ParameterizedTest
    @CsvSource({"challenger, 1", "reviewer, 2"})
    void findReviewByReviewerId(String githubId, int expectedResult) {
        PageRequest pageRequest = PageRequest.of(0, 10);

        Page<Review> reviews = reviewRepository.findAllByReviewerId(githubId, pageRequest);

        reviews.get()
                .forEach(review -> System.out.println(review.getReviewURL()));

        assertThat(reviews.getTotalElements()).isEqualTo(expectedResult);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        progressMissionRepository.deleteAll();
        reviewRepository.deleteAll();
    }

    private void saveUsers() {
        challenger = userRepository.save(new User("challenger@gmail.com", "challenger", UserRole.CHALLENGER));
        reviewer = userRepository.save(new User("reviewer@gmail.com", "reviewer", UserRole.REVIEWER));
        watcher = userRepository.save(new User("watcher@gmail.com", "watcher", UserRole.CHALLENGER));
    }

    private MissionReviewer toMissionReviewer(User user) {
        return new MissionReviewer(user.getEmail(), user.getGithubId());
    }
}