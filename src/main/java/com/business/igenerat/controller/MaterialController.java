package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Material;
import com.business.igenerat.services.MaterialService;
import com.business.utils.Root;
import com.business.utils.TipMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.TimerTask;

@RestController
@RequestMapping("/material")
@Root(roots = {1,2,3,5})
public class MaterialController {
    @Autowired
    MaterialService materialService;

    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: MaterialController.class.getAnnotation(Root.class).roots()){
            if(jid==it)
                return jid;
        }
        return 0;
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject findMaterialByName(HttpServletRequest request,String name){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(TipMap.isSuccess,true);
        jsonObject.put(TipMap.results,materialService.findMaterialByName(name));
        return jsonObject;
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteMaterial(HttpServletRequest request,Integer id) {
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        try {
            materialService.deleteMaterial(id);
            return TipMap.success();
        }catch (Exception e){
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject updateMaterial(HttpServletRequest request,Material material){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        if(materialService.updateMaterial(material))
            return TipMap.success();
        return TipMap.unknowError();
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONObject insertMaterial(HttpServletRequest request,String name,Integer inventory,String unit){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        if(materialService.insertMaterial(name,inventory,unit))
            return TipMap.success();
        return TipMap.unknowError();
    }
}
