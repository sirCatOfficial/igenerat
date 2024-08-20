package com.business.igenerat.dao;

import com.business.igenerat.pojo.Supplier;

import java.util.HashMap;
import java.util.List;

public interface SuppliersDao {
    // 插入数据的方法
    public Boolean insertSupplier(String name);

    // 根据ID删除数据的方法
    public Boolean deleteSupplierById(int id);

    // 更新数据的方法
    public Boolean updateSupplier(Supplier supplier);

    // 查询数据的方法
    public String getSupplierById(int id);

    public List<HashMap> findSuppliers(String name);
}
