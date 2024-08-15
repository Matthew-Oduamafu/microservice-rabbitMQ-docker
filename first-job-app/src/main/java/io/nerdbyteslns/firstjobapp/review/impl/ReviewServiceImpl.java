package io.nerdbyteslns.firstjobapp.review.impl;

import io.nerdbyteslns.firstjobapp.company.CompanyRepository;
import io.nerdbyteslns.firstjobapp.review.Review;
import io.nerdbyteslns.firstjobapp.review.ReviewRepository;
import io.nerdbyteslns.firstjobapp.review.ReviewService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final CompanyRepository companyRepository;

    public ReviewServiceImpl(ReviewRepository reviewRepository, CompanyRepository companyRepository) {
        this.reviewRepository = reviewRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public List<Review> findAllByCompanyId(Long companyId) {
        return reviewRepository.findByCompanyId(companyId);
    }

    @Override
    @Transactional
    public boolean create(Long companyId, Review review) {
        var company = companyRepository.findById(companyId);
        if (company.isPresent()) {
            review.setCompany(company.get());
            reviewRepository.save(review);
            return true;
        }
        return false;
    }

    @Override
    public Review findByIdAndCompanyId(Long id, Long companyId) {
        return reviewRepository.findByIdAndCompanyId(id, companyId);
    }

    @Override
    @Transactional
    public boolean deleteByIdAndCompanyId(Long id, Long companyId) {
        if (reviewRepository.existsByIdAndCompanyId(id, companyId)) {
            reviewRepository.deleteByIdAndCompanyId(id, companyId);
            return true;
        }

        return false;
    }

    @Override
    @Transactional
    public boolean update(Long id, Review review) {
        if (reviewRepository.existsByIdAndCompanyId(id, review.getCompany().getId())) {
            review.setId(id);
            reviewRepository.save(review);
            return true;
        }

        return false;
    }
}
