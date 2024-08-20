package com.business.igenerat.services;

import com.business.igenerat.pojo.Depart;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface DepartService {
    public boolean addDepart(Depart depart);
    public List<HashMap> findAll();
    public boolean updateDepart(Depart depart);
    public Boolean deleteDepart(Integer id);
    public List<HashMap> findDepart(String name);
}
