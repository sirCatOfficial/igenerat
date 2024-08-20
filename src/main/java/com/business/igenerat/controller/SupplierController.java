package com.business.igenerat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Supplier;
import com.business.igenerat.services.SupplierService;
import com.business.utils.Root;
import com.business.utils.TipMap;
import com.mysql.cj.xdevapi.TableImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/supplier")
@Root(roots = {1,2,5})
public class SupplierController {
    @Autowired
    SupplierService supplierService;
    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: SupplierController.class.getAnnotation(Root.class).roots()){
//            System.out.println(it);
            if(jid==it){
                return jid;
            }
        }
        return 0;
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONObject insertSupplier(String name){
        if(supplierService.insertSupplier(name))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject updateSupplier(Supplier supplier){
        if(supplierService.updateSupplier(supplier))
            return TipMap.success();
        return TipMap.unknowError();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteSupplier(Integer id){
        try {
            supplierService.deleteSupplierById(id);
            return TipMap.success();
        }catch (Exception e){
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "find",method = RequestMethod.GET)
    public JSONObject findSuppliers(HttpServletRequest request,String name){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        return TipMap.successFind(supplierService.findSuppliers(name));
    }

}
