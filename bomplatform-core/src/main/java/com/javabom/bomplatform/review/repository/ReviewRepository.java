package com.javabom.bomplatform.review.repository;

import com.javabom.bomplatform.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
