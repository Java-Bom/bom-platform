package com.javabom.bomplatform.web.review.service;

import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.github.business.GithubBusiness;
import com.javabom.bomplatform.core.progressmission.business.ProgressMissionBusiness;
import com.javabom.bomplatform.core.review.business.ReviewBusiness;
import com.javabom.bomplatform.slack.model.SlackMessageSender;
import com.javabom.bomplatform.web.review.controller.dto.response.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewBusiness reviewBusiness;
    private final ProgressMissionBusiness progressMissionBusiness;
    private final GithubBusiness githubBusiness;
    private final SlackMessageSender slackMessageSender;

    @Transactional
    public Long request(Long progressMissionId, String reviewURL) {
        final ProgressMission progressMission = progressMissionBusiness.showDetailById(progressMissionId);

        githubBusiness.assignReviewer(reviewURL, Arrays.asList("missionReviewers"));

        return reviewBusiness.requestReview(progressMission, reviewURL);
    }

    @Transactional
    public List<ReviewDto> getChallengerReviews(String githubId, Pageable pageable) {
        return reviewBusiness.findChallengerReviews(githubId, pageable)
                .stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ReviewDto> getReviewerReviews(String githubId, Pageable pageable) {
        return reviewBusiness.findReviewerReviews(githubId, pageable)
                .stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void completeReviewState(long reviewId, String challengerId) {
        reviewBusiness.completeReview(reviewId);
        slackMessageSender.completeReview(challengerId);
    }

    @Transactional
    public void informReviewFinish(long reviewId, String challengerId) {
        reviewBusiness.validateUncompletedReview(reviewId);
        slackMessageSender.completeReview(challengerId);
    }
}
