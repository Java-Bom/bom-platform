package com.javabom.bomplatform.web.review.service;

import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.github.business.GithubBusiness;
import com.javabom.bomplatform.core.progressmission.business.ProgressMissionBusiness;
import com.javabom.bomplatform.core.review.business.ReviewBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewBusiness reviewBusiness;
    private final ProgressMissionBusiness progressMissionBusiness;
    private final GithubBusiness githubBusiness;

    @Transactional
    public Long request(Long progressMissionId, String reviewURL) {
        final ProgressMission progressMission = progressMissionBusiness.showDetailById(progressMissionId);

        githubBusiness.assignReviewer(reviewURL, Arrays.asList("missionReviewers"));

        return reviewBusiness.requestReview(progressMission, reviewURL);
    }
}
