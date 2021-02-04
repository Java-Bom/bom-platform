package com.javabom.bomplatform.web.mission.controller.dto.response;

import com.javabom.bomplatform.core.mission.model.MissionState;
import com.javabom.bomplatform.core.mission.model.MissionStep;
import lombok.Builder;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Builder
@Data
public class MissionDto {

    private Long id;

    private String repositoryUri;

    private String fileUri;

    @Enumerated(EnumType.STRING)
    private MissionStep missionStep;

    @Enumerated(EnumType.STRING)
    private MissionState missionState;

    private String createDate;
}
