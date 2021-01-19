package com.javabom.bomplatform.github.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubService {
    void createBranch(String challenger);

    void assignReviewer(String repoUrl, List<String> missionReviewers);
}
