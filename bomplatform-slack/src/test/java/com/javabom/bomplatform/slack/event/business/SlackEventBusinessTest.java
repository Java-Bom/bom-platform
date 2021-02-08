package com.javabom.bomplatform.slack.event.business;

import com.javabom.bomplatform.event.process.EventBrokerGroup;
import com.javabom.bomplatform.event.message.SlackEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class SlackEventBusinessTest {

    @Autowired
    private EventBrokerGroup eventBrokerGroup;

    @Autowired
    private SlackEventBusiness slackEventBusiness;

    @DisplayName("Slack Event를 저장한다.")
    @Test
    public void provideTest() throws Exception {
        //given
        SlackEvent slackEvent = new SlackEvent(
                (e) -> {
                },
                "testReviewerId", "testRepoPath");

        //when
        slackEventBusiness.provide(slackEvent);

        //then
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(1);
    }

    @DisplayName("Slack Event를 소비한다.")
    @Test
    public void consumeTest() throws Exception {
        //given
        SlackEvent slackEvent = new SlackEvent(
                (e) -> {
                },
                "testReviewerId", "testRepoPath");
        slackEventBusiness.provide(slackEvent);

        //when
        slackEventBusiness.consume(slackEvent);

        //then
        assertThat(eventBrokerGroup.keySet().size()).isEqualTo(0);
    }
}
