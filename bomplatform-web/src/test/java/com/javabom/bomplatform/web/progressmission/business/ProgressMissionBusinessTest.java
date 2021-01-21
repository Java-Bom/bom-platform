package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionState;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ProgressMissionBusinessTest {

    @Autowired
    private PickMissionReviewerBusiness pickMissionReviewerBusiness;

    @Autowired
    private ProgressMissionBusiness progressMissionBusiness;

    @Autowired
    private TestEntityManager testEntityManager;

    @DisplayName("ProgressMission을 저장한다.")
    @Test
    public void begin() throws Exception {
        //given
        final Mission mission = Mission.builder()
                .repositoryUrl("repositoryUrl")
                .filePath("fileUrl")
                .missionState(MissionState.OPEN)
                .build();
        testEntityManager.persist(mission);


        final User user = User.builder()
                .email("user@test.com")
                .githubId("testUser")
                .userRole(UserRole.CHALLENGER)
                .build();
        testEntityManager.persist(user);
        testEntityManager.flush();

        final MissionReviewer missionReviewer = pickMissionReviewerBusiness.pickMissionReviewer();

        //when
        final Long progressMissionId = progressMissionBusiness.begin(mission, missionReviewer, user);
        final ProgressMission expectedProgressMission = progressMissionBusiness.showDetailById(progressMissionId);

        //then
        assertThat(progressMissionId).isEqualTo(expectedProgressMission.getId());
    }
}
