package io.nerdbyteslns.jobms.job;

import io.nerdbyteslns.jobms.job.dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();
    void create(Job job);
    JobDto findById(Long id);
    boolean delete(Long id);
    boolean update(Long id, Job job);
}
