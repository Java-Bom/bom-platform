package com.javabom.bomplatform.core.mission.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mission_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "repository_url")
    private String repositoryUrl;

    @Column(name = "file_path")
    private String filePath;

    @Enumerated(value = EnumType.STRING)
    private MissionStep missionStep;

    @Enumerated(value = EnumType.STRING)
    private MissionState missionState;

    @Builder
    public Mission(String repositoryUrl, String filePath, MissionState missionState) {
        this.repositoryUrl = repositoryUrl;
        this.filePath = filePath;
        this.missionState = missionState;
    }

    private Mission(final String repositoryUrl, final MissionStep missionStep, final String filePath) {
        this.repositoryUrl = repositoryUrl;
        this.missionStep = missionStep;
        this.filePath = filePath;
    }

    public static Mission of(final String repositoryUri, final MissionStep missionStep, final String filePath) {
        return new Mission(repositoryUri, missionStep, filePath);
    }
}
