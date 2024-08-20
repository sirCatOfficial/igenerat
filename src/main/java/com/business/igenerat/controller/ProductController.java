package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Mdate;
import com.business.igenerat.pojo.Product;
import com.business.igenerat.services.ProductImpl;
import com.business.igenerat.services.ProductService;
import com.business.utils.Root;
import com.business.utils.TipMap;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/product")
@Root(roots = {1,2,3})
public class ProductController {
    @Autowired
    ProductImpl productService;
    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: ProductController.class.getAnnotation(Root.class).roots()){
            if(jid==it)
                return jid;
        }
        return 0;
    }
//    @RequestMapping(value = "add",method = RequestMethod.POST)
//    public JSONObject addProduct(){
//
//    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONObject insertProduct(HttpServletRequest request,String name,Integer price,String unit){
        int flag=root(request);
        if(flag==-1)
            return TipMap.loginError();
        else if(flag==0)
            return TipMap.noRoot();
        else {
            if(productService.insertProduct(name,price,unit)){
                return TipMap.success();
            }
            return TipMap.unknowError();
        }
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public JSONObject addProduct(HttpServletRequest request, int id, int number, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd")Date date){
        int flag=root(request);
        if(flag==-1)
            return TipMap.loginError();
        else if(flag==0)
            return TipMap.noRoot();
        else{
            int pid=id;
            id=(Integer)request.getSession().getAttribute("id");
            if(productService.addProduce(id,pid,number,date))
                return TipMap.success();
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject findProduct(HttpServletRequest request, String name){
//        int flag=root(request);
//        if(flag==-1){
//            return TipMap.loginError();
//        }
//        else {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,productService.findProducts(name));
            return jsonObject;
//        }
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject updateProduce(HttpServletRequest request, Product product){
        int flag=root(request);
        if(flag==-1)
            return TipMap.loginError();
        else if(flag==0)
            return TipMap.noRoot();
        if(productService.updateProduct(product))
            return TipMap.success();
        return TipMap.unknowError();
    }
    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public JSONObject deleteProduct(HttpServletRequest request,Integer id){
        int flag=root(request);
        if(flag==-1)
            return TipMap.loginError();
        else if(flag==0)
            return TipMap.noRoot();
        try {
            productService.deleteProducts(id);
            return TipMap.success();
        }catch (Exception e){
            return TipMap.unknowError();
        }
    }
}
