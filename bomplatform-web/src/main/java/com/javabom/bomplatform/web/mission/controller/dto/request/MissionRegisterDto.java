package com.javabom.bomplatform.web.mission.controller.dto.request;

import com.javabom.bomplatform.core.mission.model.MissionStep;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class MissionRegisterDto {

    @ApiParam(value = "저장할 레포지토리 Url", required = true)
    private String repositoryUri;

    @ApiParam(value = "미션 단계", required = true)
    private MissionStep missionStep;
}
