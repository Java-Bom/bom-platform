package com.javabom.bomplatform.core.progressmission.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MissionReviewer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mission_reviewer")
    private Long id;

    private String email;
    private String githubId;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public MissionReviewer(String email, String githubId) {
        this.email = email;
        this.githubId = githubId;
    }
}
