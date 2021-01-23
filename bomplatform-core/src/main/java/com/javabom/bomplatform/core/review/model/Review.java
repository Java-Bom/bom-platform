package com.javabom.bomplatform.core.review.model;

import com.javabom.bomplatform.core.progressmission.model.ProgressMission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @Column(name = "review_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "progress_mission_id")
    private ProgressMission progressMission;

    @Column(name = "review_url")
    private String reviewURL;

    @Enumerated(EnumType.STRING)
    private ReviewState reviewState;

    public Review(ProgressMission progressMission, String reviewURL) {
        this.progressMission = progressMission;
        this.reviewURL = reviewURL;
        this.reviewState = ReviewState.REQUEST;
    }

    public void complete() {
        this.reviewState = ReviewState.COMPLETE;
    }
}
