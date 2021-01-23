package com.javabom.bomplatform.web.service;

import com.javabom.bomplatform.core.mission.business.MissionBusiness;
import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.mission.model.MissionStep;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MissionService {

    private final MissionBusiness missionBusiness;

    public Long register(final String repositoryUri, final MissionStep missionStep, final String filePath) {
        return missionBusiness.register(repositoryUri, missionStep, filePath);
    }

    public Mission getMission(final Long missionId) {
        return missionBusiness.findMission(missionId);
    }

    public List<Mission> getMission(final Integer pageNo, final Integer pageSize) {
        final PageRequest paging = PageRequest.of(pageNo, pageSize);
        return missionBusiness.findMission(paging);
    }
}
