package com.business.igenerat.services;

import com.business.igenerat.pojo.Customer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface CustomerService {
    public Boolean insertCustomer(String name);
    public Boolean updateCustomer(Customer customer);
    public List<Customer> findCustomers(String name);
    public Boolean deleteCustomer(Integer id);
    public List<HashMap> findByPhone(String phone);
    public Boolean registerCustomer(String name,String phone,String password);
    public List<HashMap> findByName(String name);
    public List<HashMap> findById(Integer id);
}
