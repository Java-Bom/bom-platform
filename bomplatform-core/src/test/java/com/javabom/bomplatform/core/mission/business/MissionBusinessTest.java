package com.javabom.bomplatform.core.mission.business;

import com.javabom.bomplatform.core.mission.domain.Mission;
import com.javabom.bomplatform.core.mission.domain.MissionStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
class MissionBusinessTest {

    @Autowired
    private MissionBusiness missionBusiness;

    @DisplayName("미션 등록")
    @Test
    void registerTest() {
        Long registerId = registerMission();
        Mission mission = missionBusiness.findMission(registerId);

        assertThat(mission.getId()).isEqualTo(registerId);
    }

    @DisplayName("등록된 미션 페이지 목록 조회")
    @Test
    void findMissionTest() {
        registerMission();
        registerMission();

        PageRequest pageRequest = PageRequest.of(0, 20);
        List<Mission> mission = missionBusiness.findMission(pageRequest);

        assertThat(mission.size()).isEqualTo(2);
    }

    private Long registerMission() {
        String repoUri = "testUri";
        MissionStep step = MissionStep.FIRST;
        String filePath = "testPath";

        return missionBusiness.register(repoUri, step, filePath);
    }
}