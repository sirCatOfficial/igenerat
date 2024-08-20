package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.services.ProduceService;
import com.business.igenerat.services.ProductImpl;
import com.business.utils.TipMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/produce")
public class ProduceController {
    @Autowired
    ProduceService produceService;
    @Autowired
    ProductImpl productService;

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject findProduce(HttpServletRequest request,String pname,String name){
        return TipMap.successFind(produceService.findProduce(pname,name));
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteProduce(HttpServletRequest request,Integer pid,Integer id,@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,Integer number){
        try {
            produceService.deleteProduce(pid,id,date);
            if(number==null)
                return TipMap.success();
            productService.addProduct(pid,-number);
            return TipMap.success();
        }catch (Exception e){
            return TipMap.unknowError();
        }
    }
}
