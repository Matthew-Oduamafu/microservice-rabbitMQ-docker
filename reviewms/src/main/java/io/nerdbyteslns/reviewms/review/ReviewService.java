package io.nerdbyteslns.reviewms.review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllByCompanyId(Long companyId);
    boolean create(Long companyId, Review review);
    Review findById(Long id);
    boolean deleteById(Long id);
    boolean update(Long id, Review review);
}
