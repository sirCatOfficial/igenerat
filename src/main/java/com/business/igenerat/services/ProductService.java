package com.business.igenerat.services;

import com.business.igenerat.pojo.Product;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public interface ProductService {
    public Boolean insertProduct(String name,Integer price,String unit);
    public Boolean addProduce(int id, int pid, int number, Date date);
    public Boolean addProduct(int id, int number);
    public List<Product> findProducts(String name);
    public Boolean deleteProducts(int id);
    public Boolean updateProduct(Product product);
}
