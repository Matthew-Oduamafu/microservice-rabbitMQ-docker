package io.nerdbyteslns.jobms.job.impl;


import io.nerdbyteslns.jobms.external.Company;
import io.nerdbyteslns.jobms.job.Job;
import io.nerdbyteslns.jobms.job.JobRepository;
import io.nerdbyteslns.jobms.job.JobService;
import io.nerdbyteslns.jobms.job.dto.JobWithCompanyDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDto> findAll() {
        RestTemplate restTemplate = new RestTemplate();
        var jobs = jobRepository.findAll();

        return jobs.stream().map(job -> {
            String url = "http://localhost:8081/companies/" + job.getCompanyId();
            Company company;
            try {
                company = restTemplate.getForObject(url, Company.class);
            } catch (RestClientException e) {
                if (e.getMessage().contains("404")) {
                    System.out.println("Company not found");
                    return new JobWithCompanyDto(job, null);
                }
                throw e;
            }
            return new JobWithCompanyDto(job, company);
        }).toList();
    }

    @Override
    public void create(Job job) {
        jobRepository.save(job);
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
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
