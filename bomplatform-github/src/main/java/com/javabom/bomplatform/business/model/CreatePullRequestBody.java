package com.javabom.bomplatform.business.model;

import lombok.Data;

@Data
public class CreatePullRequestBody {

    private String ref;

    private String sha;

    public CreatePullRequestBody(final String ref, final String sha) {
        this.ref = ref;
        this.sha = sha;
    }
}
