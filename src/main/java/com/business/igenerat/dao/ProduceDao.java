package com.business.igenerat.dao;

import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Configuration
public interface ProduceDao {
    public List<HashMap> findProduce(String pname,String name);
    public boolean deleteProduce(int pid, int id, Date date);

}
