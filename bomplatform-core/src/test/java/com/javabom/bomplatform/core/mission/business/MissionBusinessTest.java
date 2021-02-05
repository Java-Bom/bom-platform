package com.javabom.bomplatform.core.mission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionStep;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class MissionBusinessTest {

    @Autowired
    private MissionBusiness missionBusiness;

    @DisplayName("미션 등록")
    @Test
    void registerTest() {
        //when
        Long registerId = registerMission();
        Mission mission = missionBusiness.findMission(registerId);

        //then
        assertThat(mission.getId()).isEqualTo(registerId);
    }

    @DisplayName("Pageable를 사용한 등록된 미션 페이지 목록 조회")
    @Test
    void findMissionPageableTest() {
        //given
        registerMission();
        registerMission();
        PageRequest pageRequest = PageRequest.of(0, 20);

        //when
        List<Mission> mission = missionBusiness.findMission(pageRequest);

        //then
        assertThat(mission.size()).isEqualTo(2);
    }

    @DisplayName("등록된 미션 페이지 목록 조회")
    @Test
    void findMissionTest() {
        //given
        final Long registerId = registerMission();

        //when
        final Mission mission = missionBusiness.findMission(registerId);

        //then
        assertThat(mission.getId()).isEqualTo(registerId);
    }

    @DisplayName("등록되지 않은 미션 페이지 목록 조회시 IllegalArgumentException 발생")
    @Test
    void findMissionExceptionTest() {
        //given
        final Long registerId = registerMission();

        //when
        final Long notRegisterId = registerId + 1;

        //then
        assertThatThrownBy(() -> missionBusiness.findMission(notRegisterId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(("Not found Mission, Mission Id : " + notRegisterId));
    }

    private Long registerMission() {
        String repoUri = "testUri";
        MissionStep step = MissionStep.FIRST;
        String filePath = "testPath";

        return missionBusiness.register(repoUri, step, filePath);
    }
}