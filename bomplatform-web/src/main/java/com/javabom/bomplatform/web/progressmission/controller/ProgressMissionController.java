package com.javabom.bomplatform.web.progressmission.controller;

import com.javabom.bomplatform.web.progressmission.controller.dto.MissionBeginningRequest;
import com.javabom.bomplatform.web.progressmission.service.ProgressMissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/progressmission")
@RequiredArgsConstructor
public class ProgressMissionController {

    private final ProgressMissionService progressMissionService;

    @PostMapping("/beginning")
    public Long begin(MissionBeginningRequest missionBeginningRequest) {
        Long progressMissionId = progressMissionService.begin(
                missionBeginningRequest.getMissionId(), missionBeginningRequest.getChallengerId());
        return progressMissionId;
    }
}
