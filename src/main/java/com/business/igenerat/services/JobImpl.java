package com.business.igenerat.services;

import com.business.igenerat.dao.JobDao;
import com.business.igenerat.pojo.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobImpl implements JobService{
    @Autowired(required = false)
    JobDao jobDao;
    public boolean addJob(int did, Job job){
        try {
            return  jobDao.addJob(did,job);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<HashMap> findJobs(String name,Integer did) {
        List<HashMap> list= jobDao.findJobsByDid(name,did);
        for(HashMap map : list){
            Object na=map.get("job");
            map.remove("job");
            map.put("name",na);
        }
        return list;
    }

    @Override
    public boolean updateJob(Job job,Integer did) {
        return jobDao.updateJob(job,did);
    }


}
