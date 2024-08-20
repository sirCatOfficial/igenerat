package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Depart;
import com.business.igenerat.services.DepartImpl;
import com.business.igenerat.services.DepartService;
import com.business.utils.TipMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/depart")
public class DepartController {
    @Autowired
    DepartImpl departService;

    @RequestMapping(value = "/addDepart",method = RequestMethod.POST)
    public JSONObject addDepart(HttpServletRequest request, Depart depart){
        JSONObject jsonObject=new JSONObject();
        HttpSession session=request.getSession();
        Integer jid=(int)session.getAttribute("jid");
        if(jid==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.loginError);
        }else if(jid!=1&&jid!=2){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.noRoot);
        }
        else if(departService.addDepart(depart)){
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,TipMap.success);
        }else {
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.unknowError);
        }
        return jsonObject;
    }
    @RequestMapping(value = "/findall",method = RequestMethod.GET)
    public JSONObject findAll(HttpServletRequest request){
        JSONObject jsonObject=new JSONObject();
        HttpSession session=request.getSession();
        Object jid=session.getAttribute("jid");
        if(jid==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.loginError);
        }else{
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,departService.findAll());
        }
        return jsonObject;
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject update(HttpServletRequest request,Depart depart){
        HttpSession session=request.getSession();
        Integer jid=(int)session.getAttribute("jid");
        if(jid==null){
            return TipMap.loginError();
        }
        else if(jid!=1&&jid!=2){
            return TipMap.noRoot();
        }
        else{
            if(departService.updateDepart(depart)){
                return TipMap.success();
            }
            else
                return TipMap.unknowError();
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject delete(HttpServletRequest request, Integer id){
        HttpSession session=request.getSession();
        Integer jid=(int)session.getAttribute("jid");
        if(jid==null){
            return TipMap.loginError();
        }
        else if(jid!=1&&jid!=2){
            return TipMap.noRoot();
        }
        else{
            if(departService.deleteDepart(id))
                return TipMap.success();
            else
                return TipMap.unknowError();
        }
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject find(HttpServletRequest request, String name){
        HttpSession session=request.getSession();
        Object jid=session.getAttribute("jid");
        if(jid==null){
            return TipMap.loginError();
        }else{
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,departService.findDepart(name));
            return jsonObject;
        }
    }
}
