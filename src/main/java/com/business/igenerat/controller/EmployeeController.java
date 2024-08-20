package com.business.igenerat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Employee;
import com.business.igenerat.services.EmployeeImpl;
import com.business.utils.SmsTimer;
import com.business.utils.TipMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeImpl employeeService;
    @RequestMapping(value = "/addemployee",method = RequestMethod.POST)
    public JSONObject addEmployee(HttpServletRequest request,int jid, String name){//只有超级管理员和人事部hr有权限添加员工（未完成）
        JSONObject jsonObject=new JSONObject();
        Integer id=(Integer) request.getSession().getAttribute("id");
        Integer uJid=(Integer) request.getSession().getAttribute("jid");
        System.out.println(id);
        if(id==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results, TipMap.loginError);
        }else{
            employeeService.addEmployee(jid,name);
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results, TipMap.success);
        }
        return jsonObject;
    }

    @RequestMapping(value = "/initemployee",method = RequestMethod.POST)
    public JSONObject initEmployee(HttpServletRequest request,int id,String phone,String password,String code){
        JSONObject jsonObject=new JSONObject();
        Employee employee=employeeService.findById(id);
        Object sPhone=request.getSession().getAttribute("phone");
        if(employee==null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results, TipMap.errorId);
        }
        else if(employee.getPhone()!=null){
            jsonObject.put(TipMap.isSuccess,false);
            jsonObject.put(TipMap.results, TipMap.reRegister);
        }else if (sPhone==null||!phone.equals((String)sPhone)){ //验证手机要是否与证书一致
            return TipMap.certError();
        }else{
            String sCode= SmsTimer.getSmsCode(phone);
            if(sCode==null)//验证验证码
                return TipMap.timeLimit();
            else if(!sCode.equals(code))
                return TipMap.codeError();
            if(employeeService.initEmployee(id,phone,password)){
                SmsTimer.delSmsCode(phone);
                jsonObject.put(TipMap.isSuccess,true);
                jsonObject.put(TipMap.results, TipMap.success);
            }else {
                jsonObject.put(TipMap.isSuccess,false);
                jsonObject.put(TipMap.results, TipMap.unknowError);
            }

        }

        return jsonObject;
    }

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public JSONObject login(HttpServletRequest request, String phone, String password){
        HashMap employee=employeeService.login(phone);
        JSONObject jsonObject=new JSONObject();
        if(employee!=null){
            if(!employee.get("password").equals(password)){
                jsonObject.put(TipMap.isSuccess,false);
                jsonObject.put(TipMap.results, TipMap.passwordError);
                return jsonObject;
            }
            jsonObject.put(TipMap.isSuccess,true);
            HttpSession session=request.getSession();
            session.setAttribute("jid",employee.get("j_id"));
            employee.remove("j_id");
            session.setAttribute("id",employee.get("id"));
            session.setAttribute("name",employee.get("name"));
            session.setAttribute("phone",employee.get("phone"));
            jsonObject.put(TipMap.results, JSON.parseObject(JSON.toJSONString(employee)));
            return  jsonObject;
        }
        jsonObject.put(TipMap.isSuccess,false);
        jsonObject.put(TipMap.results, TipMap.accountError);
        return jsonObject;
    }
    @RequestMapping(value = "/find",method =RequestMethod.GET)
    public JSONObject findEmployees(HttpServletRequest request,String name,Integer jid){
        HttpSession session=request.getSession();
        Object uJid=session.getAttribute("jid");
        Object cid=session.getAttribute("cid");
        if(uJid==null&&cid==null){
            return TipMap.loginError();
        }else {
            List<HashMap> list=employeeService.findEmployees(name,jid);
            if(list==null){
                return TipMap.unknowError();
            }
            JSONObject jsonObject=new JSONObject();
            jsonObject.put(TipMap.isSuccess,true);
            jsonObject.put(TipMap.results,list);
            return jsonObject;
        }
    }
    @RequestMapping(value = "/findMyself", method = RequestMethod.GET)
    public JSONObject findMyself(HttpServletRequest request){
        HttpSession session=request.getSession();
        Object id=session.getAttribute("id");
        if(id==null){
            return TipMap.loginError();
        }else{
            Employee employee=employeeService.findById((int) id);
            if(employee==null)
                return TipMap.unknowError();
            else{
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(TipMap.isSuccess,true);
                jsonObject.put(TipMap.results,employee);
                return jsonObject;
            }
        }
    }
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public JSONObject updateEmployee(HttpServletRequest request,Employee employee,Integer jid){
        HttpSession session=request.getSession();
        Integer userJid=(Integer)session.getAttribute("jid");
        if(userJid==null){
            return TipMap.loginError();
        }else if(userJid!=1&&userJid!=2) {
            return TipMap.noRoot();
        }
        else{
            if(employeeService.updateEmployee(employee,jid)){
                return TipMap.success();
            }
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "/updateMyself",method = RequestMethod.POST)
    public JSONObject updateMyself(HttpServletRequest request,Employee employee){
        HttpSession session=request.getSession();
        Integer id= (Integer) session.getAttribute("id");
        if(id==null){
            return TipMap.loginError();
        }else if((Integer)id!=employee.getId()){
            return TipMap.noRoot();
        }else {
            if(employeeService.updateMyself(employee)){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put(TipMap.isSuccess,true);
                jsonObject.put(TipMap.results,TipMap.success());
                return jsonObject;
            }
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "/updatePhone",method = RequestMethod.POST)
    public JSONObject updatePhone(HttpServletRequest request,String phone,Integer id,String code){
        HttpSession session=request.getSession();
        Object sPhone=session.getAttribute("phone");
        if (sPhone==null||!phone.equals((String)sPhone)){ //验证手机要是否与证书一致
            return TipMap.certError();
        }else{
            String sCode= SmsTimer.getSmsCode(phone);
            if(sCode==null)//验证验证码
                return TipMap.timeLimit();
            else if(!sCode.equals(code))
                return TipMap.codeError();
            if(employeeService.updatePhone(id,phone)){
                SmsTimer.delSmsCode(phone);
                return TipMap.success();
            }else
                return TipMap.unknowError();
        }
    }

    @RequestMapping(value = "delete",method = RequestMethod.POST)
    public JSONObject deleteEmployee(HttpServletRequest request,Integer id){
        HttpSession session=request.getSession();
        Integer jid=(Integer) session.getAttribute("jid");
        if(jid==null){
            return TipMap.loginError();
        }else if((jid!=1&&jid!=2)||id==1){
            return TipMap.noRoot();
        }
        else {
            if(employeeService.deleteEmployee(id))
                return TipMap.success();
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "logout",method = RequestMethod.POST)
    public JSONObject logout(HttpSession session, SessionStatus sessionStatus){
        session.invalidate();
        sessionStatus.setComplete();
        return TipMap.success();
    }
}
