package com.business.igenerat.dao;

import com.business.igenerat.pojo.Customer;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public interface CustomerDao {
    public Boolean insertCustomer(String name);
    public Boolean updateCustomer(Customer customer);
    public List<Customer> findCustomer(String name);
    public Boolean deleteCustomer(Integer id);
    public List<HashMap> findByPhone(String phone);
    public Boolean registerCustomer(String name,String phone,String password);
    public List<HashMap> findByName(String name);
    public List<HashMap> findByid(Integer id);
}
