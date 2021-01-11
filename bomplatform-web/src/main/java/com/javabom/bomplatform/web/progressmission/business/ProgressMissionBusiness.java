package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.mission.model.Mission;
import com.javabom.bomplatform.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.progressmission.model.ProgressMission;
import com.javabom.bomplatform.progressmission.model.ProgressMissionState;
import com.javabom.bomplatform.progressmission.repository.ProgressMissionRepository;
import com.javabom.bomplatform.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class ProgressMissionBusiness {

    private final ProgressMissionRepository progressMissionRepository;

    // naming (시작을 하는데 ProgressMission을 만든다고 예측하기 어렵다고 판단
    // 후보: register
    public Long begin(Mission mission, MissionReviewer missionReviewer, User challenger) {
        final ProgressMission progressMission = ProgressMission.builder()
                .mission(mission)
                .challenger(challenger)
                .progressMissionState(ProgressMissionState.OPEN)
                .missionReviewers(asList(missionReviewer))
                .build();

        progressMissionRepository.save(progressMission);
        return progressMission.getId();
    }

    public ProgressMission findById(Long progressMissionId) {
        return progressMissionRepository.findById(progressMissionId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input id: %d, not found progressMission", progressMissionId)));
    }
}
