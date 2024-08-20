package com.business.igenerat.dao;

import com.business.igenerat.pojo.Job;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Configuration
public interface JobDao {
    public boolean addJob(int did,Job job);
    public List<HashMap> findJobsByDid(String name,Integer did);

    public boolean updateJobName(int jid,String name);

    public boolean updateJob(Job job,Integer did);

}
