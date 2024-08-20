package com.business.igenerat.services;

import com.business.igenerat.dao.CustomerDao;
import com.business.igenerat.pojo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class CustomerImpl implements CustomerService{
    @Autowired(required = false)
    CustomerDao customerDao;
    @Override
    public Boolean insertCustomer(String name) {
        return customerDao.insertCustomer(name);
    }

    @Override
    public Boolean updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    @Override
    public List<Customer> findCustomers(String name) {
        return customerDao.findCustomer(name);
    }

    @Override
    public Boolean deleteCustomer(Integer id) {
        return customerDao.deleteCustomer(id);
    }

    @Override
    public List<HashMap> findByPhone(String phone) {
        return customerDao.findByPhone(phone);
    }

    @Override
    public Boolean registerCustomer(String name, String phone, String password) {
        return customerDao.registerCustomer(name,phone,password);
    }

    @Override
    public List<HashMap> findByName(String name) {
        return customerDao.findByName(name);
    }

    @Override
    public List<HashMap> findById(Integer id) {
        return customerDao.findByid(id);
    }

}
