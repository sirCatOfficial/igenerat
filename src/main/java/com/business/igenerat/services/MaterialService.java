package com.business.igenerat.services;

import com.business.igenerat.pojo.Material;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaterialService {
    public List<Material> findMaterialByName(String name);
    public Boolean deleteMaterial(Integer id);
    public Boolean updateMaterial(Material material);
    public Boolean insertMaterial(String name,Integer inventory,String unit);
    public Boolean addMaterial(Integer id,Integer number);
}
