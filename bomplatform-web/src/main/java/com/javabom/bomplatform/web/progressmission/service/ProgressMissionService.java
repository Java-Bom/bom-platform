package com.javabom.bomplatform.web.progressmission.service;

import com.javabom.bomplatform.core.mission.business.MissionBusiness;
import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.github.business.GithubBusiness;
import com.javabom.bomplatform.core.progressmission.business.PickMissionReviewerBusiness;
import com.javabom.bomplatform.core.progressmission.business.ProgressMissionBusiness;
import com.javabom.bomplatform.core.user.business.UserBusiness;
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
    private final GithubBusiness githubBusiness;

    @Transactional
    public Long begin(final Long missionId, final Long challengerId) {
        final User challenger = userBusiness.findChallengerById(challengerId);
        final Mission mission = missionBusiness.findMission(missionId);
        githubBusiness.createBranch(mission.getRepositoryUrl(), challenger.getGithubId());
        final MissionReviewer missionReviewer = pickMissionReviewerBusiness.pickMissionReviewer();

        return progressMissionBusiness.begin(mission, missionReviewer, challenger);
    }
}
