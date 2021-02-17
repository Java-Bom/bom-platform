package com.javabom.bomplatform.web.review.controller;


import com.javabom.bomplatform.web.review.controller.dto.response.ReviewDto;
import com.javabom.bomplatform.web.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private static final String DEFAULT_PAGE_NO = "0";
    private static final String DEFAULT_PAGE_SIZE = "20";

    private final ReviewService reviewService;

    @GetMapping("/challenger/{githubId}")
    public ResponseEntity<List<ReviewDto>> getChallengerReviews(@RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
                                                                @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                                @PathVariable String githubId) {

        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<ReviewDto> reviewDtos = reviewService.getChallengerReviews(githubId, pageable);

        return ResponseEntity.ok().body(reviewDtos);
    }

    @GetMapping("/reviewer/{githubId}")
    public ResponseEntity<List<ReviewDto>> getReviewerReviews(@RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
                                                              @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,
                                                              @PathVariable String githubId) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        List<ReviewDto> reviewDtos = reviewService.getReviewerReviews(githubId, pageable);

        return ResponseEntity.ok().body(reviewDtos);
    }
}
