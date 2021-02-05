package com.javabom.bomplatform.core.progressmission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionState;
import com.javabom.bomplatform.core.mission.repository.MissionRepository;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.progressmission.repository.ProgressMissionRepository;
import com.javabom.bomplatform.core.user.model.User;
import com.javabom.bomplatform.core.user.model.UserRole;
import com.javabom.bomplatform.core.user.repository.UserRepository;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProgressMissionBusinessTest {

    @Autowired
    private PickMissionReviewerBusiness pickMissionReviewerBusiness;

    @Autowired
    private ProgressMissionBusiness progressMissionBusiness;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProgressMissionRepository progressMissionRepository;

    @DisplayName("저장된 ProgressMission 조회")
    @Test
    public void showDetailByIdTest() throws Exception {
        //given
        final Long saveId = saveProgressMission();

        //when
        final ProgressMission progressMission = progressMissionBusiness.showDetailById(saveId);

        //then
        assertThat(progressMission.getId()).isEqualTo(saveId);
    }

    @DisplayName("저장되지 않은 ProgressMission 조회 시 IllegalArgumentException발생")
    @Test
    public void findProgressMissionExceptionTest() throws Exception {
        //given
        final Long saveId = saveProgressMission();
        final Long notSaveId = saveId + 1;

        //then
        assertThatThrownBy(() -> progressMissionBusiness.showDetailById(notSaveId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("input id: %d, not found progressMission", notSaveId));
    }

    @DisplayName("ProgressMission을 저장한다.")
    @Test
    public void beginTest() throws Exception {
        //given
        final Mission mission = Mission.builder()
                .repositoryUrl("repositoryUrl")
                .filePath("filePath")
                .missionState(MissionState.OPEN)
                .build();
        missionRepository.save(mission);

        final User user = User.builder()
                .email("user@test.com")
                .githubId("testUser")
                .userRole(UserRole.CHALLENGER)
                .build();
        userRepository.save(user);

        final MissionReviewer missionReviewer = pickMissionReviewerBusiness.pickMissionReviewer();

        //when
        final Long progressMissionId = progressMissionBusiness.begin(mission, missionReviewer, user);
        final ProgressMission actualProgressMission = progressMissionBusiness.showDetailById(progressMissionId);

        //then
        assertThat(actualProgressMission.getId()).isEqualTo(progressMissionId);
    }

    private Long saveProgressMission() {
        final Mission mission = Mission.builder()
                .repositoryUrl("repositoryUrl")
                .filePath("filePath")
                .missionState(MissionState.OPEN)
                .build();
        missionRepository.save(mission);


        final User user = User.builder()
                .email("user@test.com")
                .githubId("testUser")
                .userRole(UserRole.CHALLENGER)
                .build();
        userRepository.save(user);

        final MissionReviewer missionReviewer = pickMissionReviewerBusiness.pickMissionReviewer();

        final ProgressMission progressMission = ProgressMission.builder()
                .mission(mission)
                .challenger(user)
                .missionReviewers(Arrays.asList(missionReviewer))
                .build();

        progressMissionRepository.save(progressMission);

        return progressMission.getId();
    }
}
