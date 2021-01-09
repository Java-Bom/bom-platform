package com.javabom.bomplatform.web.controller.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class MissionDtos {

    List<MissionDto> mission;

    public MissionDtos(final List<MissionDto> mission) {
        this.mission = mission;
    }
}
