package com.javabom.bomplatform.web.controller.dto.request;

import com.javabom.bomplatform.core.mission.domain.MissionStep;
import lombok.Data;

@Data
public class MissionRegisterDto {

    private String repositoryUri;

    private MissionStep missionStep;
}
