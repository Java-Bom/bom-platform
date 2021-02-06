package com.javabom.bomplatform.slack.event.message;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackEventTest {

    @DisplayName("리뷰어 아이디 반환")
    @Test
    public void getReviewerIdTest() throws Exception {
        //given
        String testReviewerId = "testReviewerId";
        String testRepoPath = "testRepoPath";
        SlackEvent slackEvent = new SlackEvent(
                (e) -> {
                },
                testReviewerId, testRepoPath);

        //when
        final String reviewerId = slackEvent.getReviewerId();

        //then
        assertThat(reviewerId).isEqualTo(testReviewerId);
    }

    @DisplayName("Repository Path 반환")
    @Test
    public void getRepoPathTest() throws Exception {
        //given
        String testReviewerId = "testReviewerId";
        String testRepoPath = "testRepoPath";
        SlackEvent slackEvent = new SlackEvent(
                (e) -> {
                },
                testReviewerId, testRepoPath);

        //when
        final String repoPath = slackEvent.getRepoPath();

        //then
        assertThat(repoPath).isEqualTo(testRepoPath);
    }
}
