package com.business.igenerat.services;

import com.business.igenerat.dao.POrderDao;
import com.business.igenerat.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface POrderService {
    public List<HashMap> selectPOrderByConditions(String sName, String mName, Integer id);
    public Boolean updatePOrder(Order order, Integer mid, Integer sid);
    public List<HashMap> findPOrderById(Integer id);
    public Boolean deletePOrder(Integer id);
    public Boolean insertPOrder(Integer mid,Integer sid,Integer eid,Order order);
}
