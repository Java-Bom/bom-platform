package com.javabom.bomplatform.web.progressmission.business;

import com.javabom.bomplatform.mission.model.Mission;
import com.javabom.bomplatform.mission.repository.MissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MissionBusiness {

    private final MissionRepository missionRepository;

    public Mission findById(Long missionId) {
        return missionRepository.findById(missionId)
                .orElseThrow(() -> new NoSuchElementException(String.format("input id: %d, not found mission", missionId)));
    }
}
