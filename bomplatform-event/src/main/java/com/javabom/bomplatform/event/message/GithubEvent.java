package com.javabom.bomplatform.event.message;



import java.util.function.Consumer;

public class GithubEvent implements Event {

    private Consumer<GithubEvent> consumer;

    private String repositoryUri;

    private String githubId;

    private byte[] body;

    public GithubEvent(final Consumer<GithubEvent> consumer, final byte[] body, final String repositoryUri, String githubId) {
        this.consumer = consumer;
        this.body = body;
        this.repositoryUri = repositoryUri;
        this.githubId = githubId;
    }

    @Override
    public void consume() {
        consumer.accept(this);
    }

    public byte[] getBody() {
        return body;
    }

    public String getRepositoryUri() {
        return repositoryUri;
    }

    public String getGithubId() {
        return githubId;
    }
}
