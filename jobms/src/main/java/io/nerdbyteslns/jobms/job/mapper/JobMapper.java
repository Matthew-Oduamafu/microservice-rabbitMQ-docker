package io.nerdbyteslns.jobms.job.mapper;

import io.nerdbyteslns.jobms.external.Company;
import io.nerdbyteslns.jobms.job.Job;
import io.nerdbyteslns.jobms.job.dto.JobWithCompanyDto;

public class JobMapper {

    public static JobWithCompanyDto toJobWithCompanyDto(Job job, Company company) {
        return new JobWithCompanyDto(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getMinSalary(),
                job.getMaxSalary(),
                job.getLocation(),
                company
        );
    }

    public static JobWithCompanyDto toJobWithCompanyDto(Job job) {
        return JobMapper.toJobWithCompanyDto(job, null);
    }
}
