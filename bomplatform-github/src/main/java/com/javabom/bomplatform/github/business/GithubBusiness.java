package com.javabom.bomplatform.github.business;

import com.javabom.bomplatform.github.model.CreateBranchParams;
import com.javabom.bomplatform.github.model.CreatePullRequestBody;
import com.javabom.bomplatform.github.model.CreatePullRequestResponse;
import com.javabom.bomplatform.github.config.GithubConfigProperties;
import com.javabom.bomplatform.github.repository.RestTemplateRepository;
import com.javabom.bomplatform.utils.GithubUriGenerator;
import com.javabom.bomplatform.utils.RepositoryUriConverter;
import com.javabom.bomplatform.utils.ShaUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GithubBusiness {

    private static final HttpHeaders headers = new HttpHeaders();

    private final RestTemplateRepository restTemplateRepository;

    private final GithubConfigProperties githubConfigProPerties;

    public ResponseEntity<CreatePullRequestResponse> createBranch(String repositoryUri, String githubId) {
        CreateBranchParams params = RepositoryUriConverter.convertCreateBranchParam(repositoryUri, githubId);
        String uri = GithubUriGenerator.generateCreateBranchRequestUri(params.getOwner(), params.getRepo());
        String ref = "refs/heads/" + params.getBranchName();
        String sha = ShaUtils.sha1(ref);
        headers.set("Authorization", githubConfigProPerties.getToken());

        CreatePullRequestBody body = new CreatePullRequestBody(ref, sha);
        HttpEntity<CreatePullRequestBody> request = new HttpEntity<>(body, headers);

        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.POST, CreatePullRequestResponse.class);
    }

    public void assignReviewer(final String reviewURL, final List<String> missionReviewers) {

    }
}
