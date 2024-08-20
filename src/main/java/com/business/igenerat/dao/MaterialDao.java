package com.business.igenerat.dao;

import com.business.igenerat.pojo.Material;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public interface MaterialDao {
    public List<Material> findMaterialByName(String name);
    public Boolean deleteMaterial(Integer id);
    public Boolean updateMaterial(Material material);
    public Boolean insertMaterial(String name,Integer inventory,String unit);
    public Boolean addMaterial(Integer id,Integer number);
}
