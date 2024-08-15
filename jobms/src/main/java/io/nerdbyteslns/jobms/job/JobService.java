package io.nerdbyteslns.jobms.job;

import io.nerdbyteslns.jobms.job.dto.JobWithCompanyDto;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDto> findAll();
    void create(Job job);
    Job findById(Long id);
    boolean delete(Long id);
    boolean update(Long id, Job job);
}
