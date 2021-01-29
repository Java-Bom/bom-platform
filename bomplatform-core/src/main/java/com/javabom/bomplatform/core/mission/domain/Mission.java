package com.javabom.bomplatform.core.mission.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Mission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String repositoryUri;

    private String fileUri;

    @Enumerated(EnumType.STRING)
    private MissionStep missionStep;

    @Enumerated(EnumType.STRING)
    private MissionState missionState;

    @CreatedDate
    private LocalDateTime createDate;


    public Mission() {
    }

    private Mission(final String repositoryUri, final MissionStep missionStep, final String fileUri) {
        this.repositoryUri = repositoryUri;
        this.missionStep = missionStep;
        this.fileUri = fileUri;
    }

    public static Mission of(final String repositoryUri, final MissionStep missionStep, final String fileUri) {
        final Mission mission = new Mission(repositoryUri, missionStep, fileUri);
        mission.changeMissionState(MissionState.OPEN);
        return mission;
    }

    public void changeMissionState(MissionState missionState) {
        this.missionState = missionState;
    }

    public void changeMissionStep(MissionStep missionStep) {
        this.missionStep = missionStep;
    }

}
