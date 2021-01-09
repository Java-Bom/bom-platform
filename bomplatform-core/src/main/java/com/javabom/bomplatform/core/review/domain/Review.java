package com.javabom.bomplatform.core.review.domain;

import com.javabom.bomplatform.core.mission.domain.ProgressMission;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ProgressMission progressMission;

    private String reviewUri;

    @Enumerated(EnumType.STRING)
    private ReviewState reviewState;

    @CreatedDate
    private LocalDateTime createdDate;

    public void closeReview() {
        this.reviewState = ReviewState.CLOSE;
    }

    public void openReview() {
        this.reviewState = ReviewState.PROGRESS;
    }
}
