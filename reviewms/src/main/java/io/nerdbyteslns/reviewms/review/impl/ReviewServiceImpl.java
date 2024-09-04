package io.nerdbyteslns.reviewms.review.impl;

import io.nerdbyteslns.reviewms.review.*;
import io.nerdbyteslns.reviewms.review.dto.CreateReviewDto;
import io.nerdbyteslns.reviewms.review.messaging.ReviewMessageProducer;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMessageProducer reviewMessageProducer;
    private final AggregateRatingRepository aggregateRatingRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, ReviewMessageProducer reviewMessageProducer, AggregateRatingRepository aggregateRatingRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMessageProducer = reviewMessageProducer;
        this.aggregateRatingRepository = aggregateRatingRepository;
    }

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public Review create(Long companyId, CreateReviewDto request) {

        if (companyId != null && request != null) {
            Review review = new Review(null, request.getTitle(), request.getDescription(), request.getRating());
            review.setCompanyId(companyId);
            review =  reviewRepository.save(review);

            AggregateRating aggregateRating = aggregateRatingRepository.findByCompanyId(companyId);
            if (aggregateRating == null) {
                aggregateRating = new AggregateRating(null, companyId, 0, 0);
            }
            aggregateRating = computeNewAvg(aggregateRating, review);

            reviewMessageProducer.sendMessage(review, aggregateRating.getAverageRating());
            return review;
        }
        return null;
    }

    private AggregateRating computeNewAvg(AggregateRating aggregateRating, Review review) {
        int totalReviews = aggregateRating.getTotalReviews() + 1;
        switch (review.getRating()) {
            case 1:
                aggregateRating.setOneStarCount(aggregateRating.getOneStarCount() + 1);
                break;
            case 2:
                aggregateRating.setTwoStarCount(aggregateRating.getTwoStarCount() + 1);
                break;
            case 3:
                aggregateRating.setThreeStarCount(aggregateRating.getThreeStarCount() + 1);
                break;
            case 4:
                aggregateRating.setFourStarCount(aggregateRating.getFourStarCount() + 1);
                break;
            case 5:
                aggregateRating.setFiveStarCount(aggregateRating.getFiveStarCount() + 1);
                break;
        }

        int oneStarCountCum = aggregateRating.getOneStarCount();
        int twoStarCountCum = aggregateRating.getTwoStarCount()*2;
        int threeStarCountCum = aggregateRating.getThreeStarCount()*3;
        int fourStarCountCum = aggregateRating.getFourStarCount()*4;
        int fiveStarCountCum = aggregateRating.getFiveStarCount()*5;

        double averageRating = (double) (oneStarCountCum + twoStarCountCum + threeStarCountCum + fourStarCountCum + fiveStarCountCum) / totalReviews;

        aggregateRating.setTotalReviews(totalReviews);
        aggregateRating.setAverageRating(averageRating);

        return aggregateRatingRepository.save(aggregateRating);
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
