package com.javabom.bomplatform.web.github.service;

import com.javabom.bomplatform.progressmission.model.MissionReviewer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GithubService {
    void createBranch(String challenger);

    void assignReviewer(String repoUrl, List<MissionReviewer> missionReviewers);
}
