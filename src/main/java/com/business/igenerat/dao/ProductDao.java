package com.business.igenerat.dao;

import com.business.igenerat.pojo.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Configuration
public interface ProductDao {
    public Boolean insertProduct(String name,Integer price,String unit);
    public boolean addProduct(int id,int number);
    public Boolean insertProduce(int pid, int id, Date date, int number);
    public List<Product> findProducts(String name);
    public Product findProduct(Integer id);
    public boolean deleteProduct(Integer id);
    public Boolean updateProduct(Product product);
}
