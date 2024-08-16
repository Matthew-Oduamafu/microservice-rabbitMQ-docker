package io.nerdbyteslns.jobms.job.impl;


import feign.FeignException;
import io.nerdbyteslns.jobms.external.Company;
import io.nerdbyteslns.jobms.external.Review;
import io.nerdbyteslns.jobms.job.Job;
import io.nerdbyteslns.jobms.job.JobRepository;
import io.nerdbyteslns.jobms.job.JobService;
import io.nerdbyteslns.jobms.job.clients.CompanyClients;
import io.nerdbyteslns.jobms.job.clients.ReviewClient;
import io.nerdbyteslns.jobms.job.dto.JobDto;
import io.nerdbyteslns.jobms.job.mapper.JobMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final RestTemplate restTemplate;
    private final CompanyClients companyClients;
    private final ReviewClient reviewClient;

    public JobServiceImpl(JobRepository jobRepository, RestTemplate restTemplate, CompanyClients companyClients, ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.restTemplate = restTemplate;
        this.companyClients = companyClients;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDto> findAll() {
        var jobs = jobRepository.findAll();

        return jobs.stream().map(job -> {
            Company company;
            List<Review> reviews;

            try{
                company = companyClients.getCompanyById(job.getCompanyId());
                reviews = reviewClient.getReviewsByCompanyId(job.getCompanyId());
            } catch (FeignException e) {
                if (e.getMessage().contains("404")) {
                    System.out.println("Company not found");
                    return JobMapper.toJobDto(job);
                }
                throw e;
            }
            return JobMapper.toJobDto(job, company, reviews);
        }).toList();
    }

    @Override
    public void create(Job job) {
        jobRepository.save(job);
    }

    @Override
    public JobDto findById(Long id) {
        var job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            return null;
        }
        JobDto jobDto = JobMapper.toJobDto(job);
        Company company = null;
        try {
            String url = "http://company-microservice:8081/companies/" + job.getCompanyId();
            company = restTemplate.getForObject(url, Company.class);
            ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange("http://review-microservice:8083/reviews?companyId=" + job.getCompanyId(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Review>>() {
            });

            jobDto.setCompany(company);
            jobDto.setReviews(reviewResponse.getBody());
        } catch (RestClientException e) {
            if (e.getMessage().contains("404")) {
                System.out.println("Company not found");
            } else {
                throw e;
            }
        }
        return jobDto;
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
