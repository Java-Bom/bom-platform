package com.javabom.bomplatform.web.progressmission.service;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.github.service.GithubService;
import com.javabom.bomplatform.web.progressmission.business.MissionBusiness;
import com.javabom.bomplatform.web.progressmission.business.PickMissionReviewerBusiness;
import com.javabom.bomplatform.web.progressmission.business.ProgressMissionBusiness;
import com.javabom.bomplatform.web.progressmission.business.UserBusiness;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgressMissionService {

    private final ProgressMissionBusiness progressMissionBusiness;
    private final UserBusiness userBusiness;
    private final MissionBusiness missionBusiness;
    private final PickMissionReviewerBusiness pickMissionReviewerBusiness;
    private final GithubService githubService;

    @Transactional
    public Long begin(final Long missionId, final Long challengerId) {
        final User challenger = userBusiness.findChallengerById(challengerId);

        githubService.createBranch(challenger.getGithubId());

        final Mission mission = missionBusiness.showDetailById(missionId);
        final MissionReviewer missionReviewer = pickMissionReviewerBusiness.pickMissionReviewer();

        return progressMissionBusiness.begin(mission, missionReviewer, challenger);
    }
}
