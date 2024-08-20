package com.business.igenerat.services;

import com.business.igenerat.dao.SuppliersDao;
import com.business.igenerat.pojo.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@ConditionalOnProperty(value="service.supplier",havingValue = "supplierImpl")
public class SupplierImpl implements SupplierService{
    @Autowired(required = false)
    SuppliersDao suppliersDao;
    @Override
    public Boolean insertSupplier(String name) {
        return suppliersDao.insertSupplier(name);
    }

    @Override
    public Boolean deleteSupplierById(int id) {
        return suppliersDao.deleteSupplierById(id);
    }

    @Override
    public Boolean updateSupplier(Supplier supplier) {
        return suppliersDao.updateSupplier(supplier);
    }

    @Override
    public Supplier getSupplierById(int id) {
        return null;
    }

    @Override
    public List<HashMap> findSuppliers(String name) {
        return suppliersDao.findSuppliers(name);
    }
}
