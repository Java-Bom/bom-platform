package com.javabom.bomplatform.web.review.controller.dto.response;

import com.javabom.bomplatform.core.review.model.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDto {
    private final LocalDateTime createdTime;
    private final String reviewUrl;
    private final String state;

    public ReviewDto(final Review review) {
        this.createdTime = review.getCreateDate();
        this.reviewUrl = review.getReviewURL();
        this.state = review.getReviewState()
                .name();
    }
}
