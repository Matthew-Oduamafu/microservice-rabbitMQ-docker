package io.nerdbyteslns.companyms.company.impl;


import io.nerdbyteslns.companyms.company.Company;
import io.nerdbyteslns.companyms.company.CompanyRepository;
import io.nerdbyteslns.companyms.company.CompanyService;
import io.nerdbyteslns.companyms.company.clients.ReviewClients;
import io.nerdbyteslns.companyms.company.dto.ReviewMessage;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ReviewClients reviewClients;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClients reviewClients) {
        this.companyRepository = companyRepository;
        this.reviewClients = reviewClients;
    }

    @Override
    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public void create(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }

    @Override
    public boolean delete(Long id) {
        if (companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        }

        return false;
    }

    @Override
    public boolean update(Long id, Company company) {
        if (companyRepository.existsById(id)) {
            company.setId(id);
            companyRepository.save(company);
            return true;
        }

        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        System.out.println("\n\n******************************************");
        System.out.println(reviewMessage.getDescription());
        System.out.println("******************************************\n\n");
        Company company = companyRepository.findById(reviewMessage.getCompanyId()).orElse(null);
        if (company != null) {
            Double currentRating = reviewClients.getAverageReview(reviewMessage.getCompanyId());
            company.setRating(currentRating);
            companyRepository.save(company);
        }
    }
}
