package com.javabom.bomplatform.core.mission.business;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionStep;
import com.javabom.bomplatform.core.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionBusiness {

    private final MissionRepository missionRepository;

    public Long register(final String repositoryUri, final MissionStep missionStep, final String filePath) {
        final Mission mission = Mission.of(repositoryUri, missionStep, filePath);
        final Mission saveMission = missionRepository.save(mission);
        return saveMission.getId();
    }

    public Mission findMission(final Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new IllegalArgumentException("Not found Mission, Mission Id : " + missionId));
    }

    public List<Mission> findMission(final Pageable pageable) {
        final Page<Mission> all = missionRepository.findAll(pageable);
        return all.getContent();
    }
}
