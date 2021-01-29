package com.javabom.bomplatform.core.mission.domain;

import com.javabom.bomplatform.core.review.domain.MissionReviewer;
import com.javabom.bomplatform.core.review.domain.Review;
import com.javabom.bomplatform.core.user.domain.User;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class ProgressMission {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Mission mission;

    @ManyToOne
    private User user;

    @CreatedDate
    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    private ProgressMissionState progressMissionState;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Review> reviews;

    @OneToMany(fetch = FetchType.LAZY)
    private List<MissionReviewer> reviewers;

    public void closeMission() {
        this.progressMissionState = ProgressMissionState.CLOSE;

        if (this.reviews != null) {
            closeReview();
        }
    }

    private void closeReview() {
        for (Review review : reviews) {
            review.closeReview();
        }
    }
}
