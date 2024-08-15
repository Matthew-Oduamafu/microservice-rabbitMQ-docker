package io.nerdbyteslns.firstjobapp.review;

import java.util.List;

public interface ReviewService {
    List<Review> findAllByCompanyId(Long companyId);
    boolean create(Long companyId, Review review);
    Review findByIdAndCompanyId(Long id, Long companyId);
    boolean deleteByIdAndCompanyId(Long id, Long companyId);
    boolean update(Long id, Review review);
}
