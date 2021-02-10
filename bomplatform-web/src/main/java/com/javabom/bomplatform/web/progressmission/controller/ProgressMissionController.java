package com.javabom.bomplatform.web.progressmission.controller;

import com.javabom.bomplatform.web.progressmission.controller.dto.MissionBeginningRequest;
import com.javabom.bomplatform.web.progressmission.service.ProgressMissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="ProgressMission")
@RestController
@RequestMapping("/v1/progressmission")
@RequiredArgsConstructor
public class ProgressMissionController {

    private final ProgressMissionService progressMissionService;

    @ApiOperation(value = "Begin Mission", notes = "미션 시작")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Invalid Mission Id or Challenger Id"),
    })
    @PostMapping("/beginning")
    public ResponseEntity<Long> begin(MissionBeginningRequest missionBeginningRequest) {
        Long progressMissionId = progressMissionService.begin(
                missionBeginningRequest.getMissionId(), missionBeginningRequest.getChallengerId());
        return ResponseEntity.ok(progressMissionId);
    }
}
