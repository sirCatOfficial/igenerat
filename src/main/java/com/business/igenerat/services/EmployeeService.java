package com.business.igenerat.services;

import com.business.igenerat.pojo.Employee;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public interface EmployeeService {
    public boolean addEmployee(int jid,String name);
    public boolean initEmployee(int id,String phone,String password);
    public HashMap login(String phone);
    public Employee findById(int id);
    public List<HashMap> findEmployees(String name,Integer jid);
    public boolean updateEmployee(Employee employee,Integer jid);
    public Boolean deleteEmployee(Integer id);
    public boolean updateMyself(Employee employee);
    public boolean updatePhone(Integer id,String phone);
}
