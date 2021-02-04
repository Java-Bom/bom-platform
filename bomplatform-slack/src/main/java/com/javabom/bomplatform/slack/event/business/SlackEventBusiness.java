package com.javabom.bomplatform.slack.event.business;

import com.javabom.bomplatform.slack.event.message.SlackEvent;
import com.javabom.bomplatform.event.process.EventBrokerGroup;
import com.javabom.bomplatform.event.service.EventService;
import com.javabom.bomplatform.slack.model.SlackMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SlackEventBusiness implements EventService<SlackEvent> {

    private final EventBrokerGroup brokerGroup;
    private final SlackMessageSender slackMessageSender;

    @Override
    public void provide(SlackEvent event) {
        brokerGroup.push(event);
    }

    @Override
    public void consume(SlackEvent event) {
        slackMessageSender.requestReview(event.getReviewerId(), event.getRepoPath());
    }
}
