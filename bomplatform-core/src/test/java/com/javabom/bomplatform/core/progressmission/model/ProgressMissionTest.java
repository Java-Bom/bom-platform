package com.javabom.bomplatform.core.progressmission.model;

import com.javabom.bomplatform.core.review.model.Review;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.*;

public class ProgressMissionTest {

    @DisplayName("진행미션의 상태를 Review 상태로 바꾼다.")
    @Test
    public void startReviewTest() throws Exception {
        //given
        ProgressMission progressMission = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.OPEN)
                .build();

        //when
        progressMission.startReview();

        //then
        assertThat(progressMission.getProgressMissionState()).isEqualTo(ProgressMissionState.REVIEW);
    }

    @DisplayName("진행미션이 닫혀있는 상태면 Exception을 발생시킨다.")
    @Test
    public void createReviewExceptionTest() throws Exception {
        //given
        ProgressMission progressMission = ProgressMission.builder()
                .progressMissionState(ProgressMissionState.CLOSE)
                .build();

        String reviewUrl = "reviewUrl.com";

        //then
        assertThatThrownBy(() -> progressMission.createReview(reviewUrl))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("progress mission is closed");
    }

    @DisplayName("진행미션이 닫혀있는 상태가 아니면 Review를 생성한다.")
    @ParameterizedTest
    @CsvSource(value = {"OPEN", "REVIEW"})
    public void createReviewSuccessTest(ProgressMissionState progressMissionState) throws Exception {
        //given
        ProgressMission progressMission = ProgressMission.builder()
                .progressMissionState(progressMissionState)
                .build();

        String reviewUrl = "reviewUrl.com";

        //when
        final Review actualReview = progressMission.createReview(reviewUrl);

        //then
        assertThat(actualReview.getReviewURL()).isEqualTo(reviewUrl);
    }
}
