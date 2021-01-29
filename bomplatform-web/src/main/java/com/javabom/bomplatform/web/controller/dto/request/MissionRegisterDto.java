package com.javabom.bomplatform.web.controller.dto.request;

import com.javabom.bomplatform.core.mission.model.MissionStep;
import lombok.Data;

@Data
public class MissionRegisterDto {

    private String repositoryUri;

    private MissionStep missionStep;
}
