package com.business.igenerat.dao;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Employee;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

@Configuration
public interface EmployeeDao {
    public void addEmployee(int jid,String name);
    public Boolean initEmployee(int id,String phone,String password);
    public HashMap login(String phone);
    public Employee findById(int id);
    public List<HashMap> findEmployees(String name, Integer jid);
    public boolean updateEmployee(Employee employee,Integer jid);
    public boolean deleteEmployee(Integer id);
    public boolean updateMyself(Employee employee);
    public boolean updatePhone(Integer id,String phone);
}
