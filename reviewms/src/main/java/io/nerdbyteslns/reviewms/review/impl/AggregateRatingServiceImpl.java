package io.nerdbyteslns.reviewms.review.impl;

import io.nerdbyteslns.reviewms.review.AggregateRating;
import io.nerdbyteslns.reviewms.review.AggregateRatingRepository;
import io.nerdbyteslns.reviewms.review.AggregateRatingService;
import org.springframework.stereotype.Service;

@Service
public class AggregateRatingServiceImpl implements AggregateRatingService {
    private final AggregateRatingRepository aggregateRatingRepository;

    public AggregateRatingServiceImpl(AggregateRatingRepository aggregateRatingRepository) {
        this.aggregateRatingRepository = aggregateRatingRepository;
    }


    @Override
    public AggregateRating getAggregateRatingByCompanyId(Long companyId) {
        return aggregateRatingRepository.findByCompanyId(companyId);
    }
}
