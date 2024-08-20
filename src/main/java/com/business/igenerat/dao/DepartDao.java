package com.business.igenerat.dao;

import com.business.igenerat.pojo.Depart;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
public interface DepartDao {
    public void addDepart(Depart depart);

    public List<HashMap> findAll();

    public boolean updateDepart(Depart depart);
    public boolean deleteDepart(Integer id);
    public List<HashMap> findDepart(String name);
}
