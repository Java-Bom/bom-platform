package com.javabom.bomplatform.core.review.repository;

import com.javabom.bomplatform.core.review.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
