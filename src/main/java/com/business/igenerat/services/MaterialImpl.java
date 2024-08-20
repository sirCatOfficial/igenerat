package com.business.igenerat.services;

import com.business.igenerat.dao.MaterialDao;
import com.business.igenerat.pojo.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(value="service.material",havingValue = "MaterialImpl")
public class MaterialImpl implements MaterialService{
    @Autowired(required = false)
    MaterialDao materialDao;
    @Override
    public List<Material> findMaterialByName(String name) {
        return materialDao.findMaterialByName(name);
    }

    @Override
    public Boolean deleteMaterial(Integer id) {
        return materialDao.deleteMaterial(id);
    }

    @Override
    public Boolean updateMaterial(Material material) {
        return materialDao.updateMaterial(material);
    }

    @Override
    public Boolean insertMaterial(String name, Integer inventory, String unit) {
        return materialDao.insertMaterial(name,inventory,unit);
    }

    @Override
    public Boolean addMaterial(Integer id,Integer number) {
        return  materialDao.addMaterial(id,number);
    }
}
