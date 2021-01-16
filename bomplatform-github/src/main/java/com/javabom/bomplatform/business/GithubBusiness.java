package com.javabom.bomplatform.business;

import com.javabom.bomplatform.business.model.Branches;
import com.javabom.bomplatform.business.model.CreatePullRequestResponse;
import com.javabom.bomplatform.business.model.GithubRepoResponse;
import com.javabom.bomplatform.repository.RestTemplateRepository;
import com.javabom.bomplatform.utils.GithubUriGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;


@Slf4j
@Service
public class GithubBusiness {

    private static final HttpHeaders headers = new HttpHeaders();

    private final RestTemplateRepository restTemplateRepository;

    @Value("${github.token}")
    public String token;

    public GithubBusiness(final RestTemplateRepository restTemplateRepository) {
        this.restTemplateRepository = restTemplateRepository;
    }

    public List<GithubRepoResponse> getRepo(String org) {
        String uri = GithubUriGenerator.generateGetRepoRequestUri(org);

        headers.set("Authorization", token);
        HttpEntity<GithubRepoResponse> request = new HttpEntity<>(headers);
        return (List<GithubRepoResponse>) restTemplateRepository.call(URI.create(uri), request, HttpMethod.GET, List.class).getBody();
    }

    public ResponseEntity getPullRequests(String owner, String repo) {
        String uri = GithubUriGenerator.generateGetPullRequests(owner, repo);

        headers.set("Authorization", token);
        HttpEntity<GithubRepoResponse> request = new HttpEntity<>(headers);
        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.GET, GithubRepoResponse.class);
    }

    public ResponseEntity createPullRequest(String owner, String repo) {
        String uri = GithubUriGenerator.generateCreatePullRequestUri(owner, repo);
        headers.set("Authorization", token);
        HttpEntity<GithubRepoResponse> request = new HttpEntity<>(headers);

        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.POST, CreatePullRequestResponse.class);
    }

    public ResponseEntity<GithubRepoResponse> getRepoBranches(String owner, String repo) {
        String uri = GithubUriGenerator.generateGetBranchesRequestUri(owner, repo);

        headers.set("Authorization", token);
        HttpEntity<Branches> request = new HttpEntity<>(headers);
        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.GET, List.class);
    }

    public ResponseEntity requestReview(String owner, String repo, Integer pullNumber) {
        String uri = GithubUriGenerator.generateRequestReviewRequestUri(owner, repo, pullNumber);

        headers.set("Authorization", token);
        HttpEntity<Branches> request = new HttpEntity<>(headers);
        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.POST, GithubRepoResponse.class);
    }

    public ResponseEntity createBranch(String owner, String repo) {
        String uri = GithubUriGenerator.generateCreateBranchRequestUri(owner, repo);

        headers.set("Authorization", token);
        HttpEntity<Branches> request = new HttpEntity<>(headers);
        return restTemplateRepository.call(URI.create(uri), request, HttpMethod.POST, GithubRepoResponse.class);
    }
}
