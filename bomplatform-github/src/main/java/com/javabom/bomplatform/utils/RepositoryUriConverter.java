package com.javabom.bomplatform.utils;

import com.javabom.bomplatform.github.model.CreateBranchParams;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RepositoryUriConverter {

    public static CreateBranchParams convertCreateBranchParam(String repoUri, String githubId) {
        int lastIdx = repoUri.lastIndexOf("/");
        final String[] path = repoUri.split("/");
        String repo = path[lastIdx];
        String owner = path[lastIdx - 1];

        return CreateBranchParams.of(owner, repo, githubId);
    }
}
