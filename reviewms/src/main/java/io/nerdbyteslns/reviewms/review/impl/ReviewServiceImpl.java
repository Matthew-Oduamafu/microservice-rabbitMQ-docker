package io.nerdbyteslns.reviewms.review.impl;

import io.nerdbyteslns.reviewms.review.Review;
import io.nerdbyteslns.reviewms.review.ReviewRepository;
import io.nerdbyteslns.reviewms.review.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public boolean create(Long companyId, Review review) {

        if (companyId != null && review != null) {
            review.setCompanyId(companyId);
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review findById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public boolean deleteById(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean update(Long id, Review review) {
        if (reviewRepository.existsById(id)) {
            review.setId(id);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }
}
