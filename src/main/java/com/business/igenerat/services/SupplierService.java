package com.business.igenerat.services;

import com.business.igenerat.pojo.Supplier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public interface SupplierService {
    public Boolean insertSupplier(String name);
    public Boolean deleteSupplierById(int id);
    public Boolean updateSupplier(Supplier supplier);
    public Supplier getSupplierById(int id);
    public List<HashMap> findSuppliers(String name);
}
