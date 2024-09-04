package io.nerdbyteslns.reviewms.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregateRatingRepository extends JpaRepository<AggregateRating, Long> {
    AggregateRating findByCompanyId(Long companyId);
}
