package com.javabom.bomplatform.web.progressmission.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MissionBeginningRequest {
    private Long missionId;
    private Long challengerId;
}
