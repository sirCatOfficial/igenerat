package com.business.igenerat.services;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.business.igenerat.dao.EmployeeDao;
import com.business.igenerat.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeImpl implements EmployeeService{
    @Autowired(required = false)
    EmployeeDao employeeDao;
    @Override
    public boolean addEmployee(int jid,String name) {
        try {
            employeeDao.addEmployee(jid,name);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean initEmployee(int id, String phone, String password) {
//        初始化员工，员工自己填写信息
        try {
            System.out.println(employeeDao.initEmployee(id,phone,password));
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public HashMap login(String phone) {
//        System.out.println(employeeDao.login(phone));
        try {
//            JSONObject employeeJson=employeeDao.login(phone);
            return employeeDao.login(phone);
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Employee findById(int id) {
        return employeeDao.findById(id);
    }

    @Override
    public List<HashMap> findEmployees(String name, Integer jid) {
        try {
            return employeeDao.findEmployees(name,jid);
        }catch (Exception e){
            return null;
        }

    }

    @Override
    public boolean updateEmployee(Employee employee, Integer jid) {
        return employeeDao.updateEmployee(employee,jid);
    }

    @Override
    public Boolean deleteEmployee(Integer id) {
        try {
            return employeeDao.deleteEmployee(id);
        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateMyself(Employee employee) {
        return employeeDao.updateMyself(employee);
    }

    @Override
    public boolean updatePhone(Integer id, String phone) {
        return employeeDao.updatePhone(id,phone);
    }


}
