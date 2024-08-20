package com.business.igenerat.services;

import com.business.igenerat.dao.ProductDao;
import com.business.igenerat.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class ProductImpl implements ProductService{
    @Autowired(required = false)
    ProductDao productDao;
    @Override
    public Boolean insertProduct(String name,Integer price, String unit) {
        return productDao.insertProduct(name,price,unit);
    }

    @Override
    public Boolean addProduce(int id, int pid, int number, Date date) {
        return productDao.insertProduce(pid,id,date,number)&&productDao.addProduct(pid,number);
    }

    @Override
    public Boolean addProduct(int id, int number) {
        if(productDao.findProduct(id).getInventory()+number<0)
            return false;
        productDao.addProduct(id,number);
        return true;
    }

    @Override
    public List<Product> findProducts(String name) {
        return productDao.findProducts(name);
    }

    @Override
    public Boolean deleteProducts(int id) {
        return productDao.deleteProduct(id);
    }

    @Override
    public Boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }
}
