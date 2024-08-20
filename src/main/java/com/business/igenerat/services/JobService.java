package com.business.igenerat.services;

import com.business.igenerat.pojo.Job;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface JobService {
    public boolean addJob(int did, Job job);
    public List<HashMap> findJobs(String name,Integer did);
    public boolean updateJob(Job job,Integer did);
}
