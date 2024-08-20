package com.business.igenerat.services;

import com.business.igenerat.dao.POrderDao;
import com.business.igenerat.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class POrderImpl implements POrderService{
    @Autowired(required = false)
    POrderDao pOrderDao;
    @Override
    public List<HashMap> selectPOrderByConditions(String sName, String mName, Integer id) {
        return pOrderDao.findPOrders(sName, mName, id);
    }

    @Override
    public Boolean updatePOrder(Order order, Integer mid, Integer sid) {
        return pOrderDao.updatePOrder(order,mid,sid);
    }

    @Override
    public List<HashMap> findPOrderById(Integer id) {
        return pOrderDao.findPOrderById(id);
    }

    @Override
    public Boolean deletePOrder(Integer id) {
        return pOrderDao.deletePOrder(id);
    }
    public Boolean insertPOrder(Integer mid,Integer sid,Integer eid,Order order){
        return pOrderDao.insertPOrder(mid,sid,eid,order);
    }
}
