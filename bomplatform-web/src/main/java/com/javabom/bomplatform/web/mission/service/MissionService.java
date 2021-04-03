package com.javabom.bomplatform.web.mission.service;

import com.javabom.bomplatform.core.mission.business.MissionBusiness;
import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionBusiness missionBusiness;

    @Transactional
    public Long register(final String repositoryUri, final MissionStep missionStep, final String filePath) {
        return missionBusiness.register(repositoryUri, missionStep, filePath);
    }

    public Mission getMission(final Long missionId) {
        return missionBusiness.findMission(missionId);
    }

    public List<Mission> getMission(Pageable pageable) {
        return missionBusiness.findMission(pageable);
    }
}
