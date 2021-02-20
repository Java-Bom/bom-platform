package com.javabom.bomplatform.web.review.controller.dto.response;

import com.javabom.bomplatform.core.review.model.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ReviewDto {
    private LocalDateTime createdTime;
    private String reviewUrl;
    private String state;

    public ReviewDto(final Review review) {
        this.createdTime = review.getCreateDate();
        this.reviewUrl = review.getReviewURL();
        this.state = review.getReviewState()
                .name();
    }
}
