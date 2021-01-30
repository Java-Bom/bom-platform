package com.javabom.bomplatform.slack.event.message;

import com.javabom.bomplatform.event.message.Event;

import java.util.function.Consumer;

public class SlackEvent implements Event {

    protected Consumer<SlackEvent> eventConsumer;
    private final String reviewerId;
    private final String repoPath;

    public SlackEvent(Consumer<SlackEvent> eventConsumer, String reviewerId, String repoPath) {
        this.eventConsumer = eventConsumer;
        this.reviewerId = reviewerId;
        this.repoPath = repoPath;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public String getRepoPath() {
        return repoPath;
    }

    @Override
    public void consume() {
        this.eventConsumer.accept(this);
    }
}
