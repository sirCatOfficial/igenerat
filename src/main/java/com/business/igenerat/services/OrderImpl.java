package com.business.igenerat.services;

import com.business.igenerat.dao.OrderDao;
import com.business.igenerat.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderImpl implements OrderService{
    @Autowired(required = false)
    OrderDao orderDao;
    @Override
    public Boolean insertOrder(Order order, Integer pid, Integer cid, Integer eid) {
        return orderDao.insertOrder(order,pid,cid,eid);
    }

    @Override
    public Boolean updateOrder(Order order, Integer pid,Integer cid) {
        return orderDao.updateOrder(order,pid,cid);
    }

    @Override
    public List<HashMap> findOrder(String pname,String cname) {
        return orderDao.findOrder(pname,cname);
    }

    @Override
    public Boolean deleteOrder(Integer id) {
        return orderDao.deleteOrder(id);
    }

    @Override
    public List<HashMap> findOrderById(Integer id) {
        return orderDao.findOrderById(id);
    }

    @Override
    public List<HashMap> findMyOrder(Integer cid) {
        return orderDao.findMyOrder(cid);
    }

}
