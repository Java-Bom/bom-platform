package com.javabom.bomplatform.slack.model;

import com.javabom.bomplatform.slack.property.MessageSenderProperty;
import net.gpedro.integrations.slack.SlackApi;
import net.gpedro.integrations.slack.SlackMessage;
import org.springframework.stereotype.Service;

@Service
public class SlackMessageSender {

    private final SlackApi slackApi;
    private final MessageSenderProperty messageSenderProperty;

    public SlackMessageSender(MessageSenderProperty messageSenderProperty) {
        this.messageSenderProperty = messageSenderProperty;
        this.slackApi = new SlackApi(messageSenderProperty.getWebHookUrl());
    }

    public void requestReview(String reviewerId, String reviewUrl) {
        SlackMessage message = new SlackMessage(makeRequestMessage(convertToLinkName(reviewerId), reviewUrl));
        slackApi.call(message);
    }

    public void completeReview(String challengerId) {
        SlackMessage message = new SlackMessage(makeCompleteMessage(convertToLinkName(challengerId)));
        slackApi.call(message);
    }

    private String makeRequestMessage(String userId, String reviewUrl) {
        return userId + " 리뷰 요청" + "\nPR link: " + reviewUrl;
    }

    private String makeCompleteMessage(String userId) {
        return userId + " 리뷰 완료";
    }

    private String convertToLinkName(String userId) {
        return "<@" + userId + ">";
    }
}
