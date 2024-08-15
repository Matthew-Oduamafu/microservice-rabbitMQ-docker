package io.nerdbyteslns.jobms.job.impl;


import io.nerdbyteslns.jobms.external.Company;
import io.nerdbyteslns.jobms.job.Job;
import io.nerdbyteslns.jobms.job.JobRepository;
import io.nerdbyteslns.jobms.job.JobService;
import io.nerdbyteslns.jobms.job.dto.JobWithCompanyDto;
import io.nerdbyteslns.jobms.job.mapper.JobMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<JobWithCompanyDto> findAll() {
        var jobs = jobRepository.findAll();

        return jobs.stream().map(job -> {
            String url = "http://company-microservice:8081/companies/" + job.getCompanyId();
            Company company;
            try {
                company = restTemplate.getForObject(url, Company.class);
            } catch (RestClientException e) {
                if (e.getMessage().contains("404")) {
                    System.out.println("Company not found");
                    return JobMapper.toJobWithCompanyDto(job);
                }
                throw e;
            }
            return JobMapper.toJobWithCompanyDto(job, company);
        }).toList();
    }

    @Override
    public void create(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobWithCompanyDto findById(Long id) {
        var job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }
        JobWithCompanyDto jobWithCompanyDto = JobMapper.toJobWithCompanyDto(job);
        Company company = null;
        try {
            String url = "http://company-microservice:8081/companies/" + job.getCompanyId();
            company = restTemplate.getForObject(url, Company.class);
            jobWithCompanyDto.setCompany(company);
        } catch (RestClientException e) {
            if (e.getMessage().contains("404")) {
                System.out.println("Company not found");
            } else {
                throw e;
            }
        }
        return jobWithCompanyDto;
    }

    @Override
    public boolean delete(Long id) {
        if (jobRepository.existsById(id)) {
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Long id, Job job) {
        if (jobRepository.existsById(id)) {
            job.setId(id);
            jobRepository.save(job);
            return true;
        }
        return false;
    }
}
