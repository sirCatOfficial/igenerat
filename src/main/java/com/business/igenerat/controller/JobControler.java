package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Job;
import com.business.igenerat.services.JobImpl;
import com.business.utils.TipMap;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/job")
public class JobControler {
    @Autowired
    JobImpl jobService;
    @RequestMapping(value = "findjob" ,method = RequestMethod.GET)
    public JSONObject findJobs(HttpServletRequest request,String name, Integer did){
        Integer id=(Integer) request.getSession().getAttribute("id");
        JSONObject jsonObject=new JSONObject();
        if(id==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.loginError);
        }else {
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,jobService.findJobs(name,did));
        }
        return jsonObject;
    }

    @RequestMapping(value = "addjob" ,method = RequestMethod.POST)
    public JSONObject addJob(HttpServletRequest request,int did,Job job){
        Integer id=(Integer) request.getSession().getAttribute("jid");
        JSONObject jsonObject=new JSONObject();
        if(id==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.loginError);
        }else if(id!=1){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results,TipMap.noRoot);
        }
        else {
            boolean isSuccess=jobService.addJob(did,job);
            if(isSuccess){
                jsonObject.put(TipMap.isSuccess,true);
                jsonObject.put(TipMap.results,TipMap.success);
            }
            else{
                jsonObject.put(TipMap.isSuccess,false);
                jsonObject.put(TipMap.results,TipMap.unknowError);
            }
        }

        return jsonObject;
    }

    @RequestMapping(value = "update" ,method = RequestMethod.POST)
    public JSONObject updateJob(HttpServletRequest request,Job job,Integer did){
//        System.out.println(job+" "+did);
        Integer jid=(Integer) request.getSession().getAttribute("jid");
        if(jid==null){
            return TipMap.loginError();
        }else if(jid!=1&&jid!=2){
            return TipMap.noRoot();
        }else{
            if(jobService.updateJob(job,did))
                return TipMap.success();
            else
                return TipMap.unknowError();
        }
    }
}
