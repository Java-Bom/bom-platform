package com.javabom.bomplatform.github.model;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class CreatePullRequestResponse {

    private String ref;

    private String nodeId;

    private String url;

    private PullRequestInfo object;

    @Data
    private final class PullRequestInfo {

        private String type;

        private String sha;

        private String url;
    }
}
