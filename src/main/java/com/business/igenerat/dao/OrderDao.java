package com.business.igenerat.dao;

import com.business.igenerat.pojo.Order;
import io.swagger.models.auth.In;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
public interface OrderDao {
    public Boolean insertOrder(Order order,Integer pid,Integer cid,Integer eid);
    public Boolean updateOrder(Order order, Integer pid,Integer cid);
    public List<HashMap> findOrder(String pname,String cname);
    public Boolean deleteOrder(Integer id);
    public List<HashMap> findOrderById(Integer id);
    public List<HashMap> findMyOrder(Integer cid);
}
