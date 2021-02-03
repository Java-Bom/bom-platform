package com.javabom.bomplatform.core.progressmission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.progressmission.model.MissionReviewer;
import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import com.javabom.bomplatform.core.progressmission.model.ProgressMissionState;
import com.javabom.bomplatform.core.progressmission.repository.ProgressMissionRepository;
import com.javabom.bomplatform.core.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static java.util.Arrays.asList;

@Service
@RequiredArgsConstructor
public class ProgressMissionBusiness {

    private final ProgressMissionRepository progressMissionRepository;

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

    public ProgressMission showDetailById(Long progressMissionId) {
        return progressMissionRepository.findById(progressMissionId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input id: %d, not found progressMission", progressMissionId)));
    }
}
