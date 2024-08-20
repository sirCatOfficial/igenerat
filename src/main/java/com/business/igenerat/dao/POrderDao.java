package com.business.igenerat.dao;

import com.business.igenerat.pojo.Order;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
@Configuration
public interface POrderDao {
    public List<HashMap> findPOrders(String sName,String mName,Integer id);
    public Boolean updatePOrder(Order order, Integer mid, Integer sid);
    public List<HashMap> findPOrderById(Integer id);
    public Boolean deletePOrder(Integer id);
    public Boolean insertPOrder(Integer mid,Integer sid,Integer eid,Order order);
}
