package io.nerdbyteslns.reviewms.review;

import io.nerdbyteslns.reviewms.review.dto.CreateReviewDto;

import java.util.List;

public interface ReviewService {
    List<Review> findAllByCompanyId(Long companyId);
    Review create(Long companyId, CreateReviewDto request);
    Review findById(Long id);
    boolean deleteById(Long id);
    boolean update(Long id, Review review);
}
