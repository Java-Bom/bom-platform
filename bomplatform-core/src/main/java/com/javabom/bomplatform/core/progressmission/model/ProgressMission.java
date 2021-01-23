package com.javabom.bomplatform.core.progressmission.model;

import com.javabom.bomplatform.core.mission.model.Mission;
import com.javabom.bomplatform.core.review.model.Review;
import com.javabom.bomplatform.core.user.model.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.javabom.bomplatform.core.progressmission.model.ProgressMissionState.CLOSE;
import static com.javabom.bomplatform.core.progressmission.model.ProgressMissionState.REVIEW;
import static javax.persistence.FetchType.LAZY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProgressMission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "progress_mission_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "challenger_id")
    private User challenger;

    @OneToMany(fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<MissionReviewer> missionReviewers;

    @CreatedDate
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private ProgressMissionState progressMissionState;

    @Builder
    public ProgressMission(Mission mission, User challenger,
                           List<MissionReviewer> missionReviewers, ProgressMissionState progressMissionState) {
        this.mission = mission;
        this.challenger = challenger;
        this.missionReviewers = missionReviewers;
        this.progressMissionState = progressMissionState;
    }

    public Review createReview(String reviewURL) {
        verifyNotCloseProgressMission();
        return new Review(this, reviewURL);
    }

    public void startReview() {
        progressMissionState = REVIEW;
    }

    private void verifyNotCloseProgressMission() {
        if (this.progressMissionState == CLOSE) {
            throw new IllegalArgumentException("progress mission is closed");
        }
    }
}
