package com.javabom.bomplatform.web.progressmission.controller.dto;

import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissionBeginningRequest {

    @ApiParam(value = "시작한 미션 ID", required = true)
    private Long missionId;

    @ApiParam(value = "시작할 유저 ID", required = true)
    private Long challengerId;
}
