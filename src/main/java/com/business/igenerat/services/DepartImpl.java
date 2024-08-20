package com.business.igenerat.services;

import com.business.igenerat.dao.DepartDao;
import com.business.igenerat.pojo.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class DepartImpl implements DepartService{
    @Autowired(required = false)
    DepartDao departDao;
    @Override
    public boolean addDepart(Depart depart) {
        try {
            departDao.addDepart(depart);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<HashMap> findAll(){
        return departDao.findAll();
    }

    @Override
    public boolean updateDepart(Depart depart) {
        return departDao.updateDepart(depart);
    }

    @Override
    public Boolean deleteDepart(Integer id) {
        try {
            return departDao.deleteDepart(id);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<HashMap> findDepart(String name) {
        return departDao.findDepart(name);
    }
}
