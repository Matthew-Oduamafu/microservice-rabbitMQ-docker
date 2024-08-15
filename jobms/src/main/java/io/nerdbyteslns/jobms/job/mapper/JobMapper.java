package io.nerdbyteslns.jobms.job.mapper;

import io.nerdbyteslns.jobms.external.Company;
import io.nerdbyteslns.jobms.external.Review;
import io.nerdbyteslns.jobms.job.Job;
import io.nerdbyteslns.jobms.job.dto.JobDto;

import java.util.List;

public class JobMapper {

    public static JobDto toJobDto(Job job, Company company, List<Review> reviews) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getLocation(),
                company,
                reviews
        );
    }
    public static JobDto toJobDto(Job job, Company company) {
        return JobMapper.toJobDto(job, company, null);
    }
    public static JobDto toJobDto(Job job, List<Review> reviews) {
        return JobMapper.toJobDto(job, null, reviews);
    }

    public static JobDto toJobDto(Job job) {
        return JobMapper.toJobDto(job, null, null);
    }
}
