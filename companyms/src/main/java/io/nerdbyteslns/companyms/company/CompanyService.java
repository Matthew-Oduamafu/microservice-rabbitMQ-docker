package io.nerdbyteslns.companyms.company;

import java.util.List;

public interface CompanyService {
    List<Company> findAll();
    void create(Company company);
    Company findById(Long id);
    boolean delete(Long id);
    boolean update(Long id, Company company);
}
