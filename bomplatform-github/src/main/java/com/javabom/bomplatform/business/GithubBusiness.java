package com.javabom.bomplatform.business;

import com.javabom.bomplatform.business.model.CreatePullRequestBody;
import com.javabom.bomplatform.business.model.CreatePullRequestResponse;
import com.javabom.bomplatform.config.GithubConfigProperties;
import com.javabom.bomplatform.repository.RestTemplateRepository;
import com.javabom.bomplatform.utils.GithubUriGenerator;
import com.javabom.bomplatform.utils.ShaUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;


@Slf4j
@Service
public class GithubBusiness {

    private static final HttpHeaders headers = new HttpHeaders();

    private final RestTemplateRepository restTemplateRepository;

    private final GithubConfigProperties githubConfigProPerties;


    public GithubBusiness(RestTemplateRepository restTemplateRepository, GithubConfigProperties githubConfigProPerties) {
        this.restTemplateRepository = restTemplateRepository;
        this.githubConfigProPerties = githubConfigProPerties;
    }

    public ResponseEntity<CreatePullRequestResponse> createPullRequest(String owner, String repo, String ref) {
        String uri = GithubUriGenerator.generateCreatePullRequestUri(owner, repo);
        headers.set("Authorization", githubConfigProPerties.getToken());

        CreatePullRequestBody body = new CreatePullRequestBody(ref, ShaUtils.sha1(ref));
        HttpEntity<CreatePullRequestBody> request = new HttpEntity<>(body, headers);

        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.POST, CreatePullRequestResponse.class);
    }
}
