package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionState;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MissionBusinessTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MissionBusiness missionBusiness;

    @DisplayName("Mission을 찾는다.")
    @Test
    public void showDetailByIdTest() throws Exception {
        //given
        final Mission mission = Mission.builder()
                .repositoryUrl("/mission/repo")
                .filePath("/mission/path")
                .missionState(MissionState.OPEN)
                .build();

        testEntityManager.persist(mission);
        testEntityManager.flush();

        //when
        final Mission findMission = missionBusiness.showDetailById(mission.getId());

        //then
        Assertions.assertThat(findMission.getId()).isEqualTo(mission.getId());
    }
}
