package com.business.igenerat.services;

import com.business.igenerat.pojo.Order;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface OrderService {
    public Boolean insertOrder(Order order, Integer pid, Integer cid, Integer eid);
    public Boolean updateOrder(Order order, Integer pid,Integer cid);
    public List<HashMap> findOrder(String pname,String cname);
    public Boolean deleteOrder(Integer id);
    public List<HashMap> findOrderById(Integer id);
    public List<HashMap> findMyOrder(Integer cid);
}
