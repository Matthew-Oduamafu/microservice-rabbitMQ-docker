package io.nerdbyteslns.firstjobapp.company.impl;

import io.nerdbyteslns.firstjobapp.company.Company;
import io.nerdbyteslns.firstjobapp.company.CompanyRepository;
import io.nerdbyteslns.firstjobapp.company.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
}
