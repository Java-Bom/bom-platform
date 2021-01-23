package com.javabom.bomplatform.web.controller.dto.response;

import com.javabom.bomplatform.core.mission.domain.MissionState;
import com.javabom.bomplatform.core.mission.domain.MissionStep;
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
