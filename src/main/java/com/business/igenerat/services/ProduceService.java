package com.business.igenerat.services;

import com.business.igenerat.dao.ProduceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProduceService {
    @Autowired(required = false)
    ProduceDao produceDao;
    public List<HashMap> findProduce(String pname,String name){
        return produceDao.findProduce(pname,name);
    }
    public boolean deleteProduce(int pid, int id, Date date){
        return produceDao.deleteProduce(pid,id,date);
    }
}
