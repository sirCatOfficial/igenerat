package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Customer;
import com.business.igenerat.services.CustomerImpl;
import com.business.utils.Root;
import com.business.utils.SmsTimer;
import com.business.utils.TipMap;
import com.sun.org.apache.bcel.internal.classfile.Code;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/customer")
@Root(roots = {1,2,4})
public class CustomerController {
    @Autowired
    CustomerImpl customerService;

    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: CustomerController.class.getAnnotation(Root.class).roots()){
            if(jid==it)
                return jid;
        }
        return 0;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JSONObject registerCustomer(HttpServletRequest request, Customer customer, String code) {
        if (!customerService.findByPhone(customer.getPhone()).isEmpty()) {
            return TipMap.reRegister();
        }
        List<HashMap> list= customerService.findByName(customer.getName());
        Object sPhone = request.getSession().getAttribute("phone");
        if (sPhone == null || !customer.getPhone().equals((String) sPhone)) //验证手机要是否与证书一致
            return TipMap.certError();
        String sCode = SmsTimer.getSmsCode(customer.getPhone());
        if (sCode == null)//验证验证码
            return TipMap.timeLimit();
        else if (!sCode.equals(code))
            return TipMap.codeError();

        if (!list.isEmpty()) {
            HashMap map = list.get(0);
            if (map.get("phone") != null)
                return TipMap.reRegister();
            customer.setId((Integer) map.get("id"));
            if (customerService.updateCustomer(customer))
                return TipMap.success();
            return TipMap.unknowError();
        }
        if (customerService.registerCustomer(customer.getName(), customer.getPhone(), customer.getPassword()))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public JSONObject insertCustomer(HttpServletRequest request, String name) {
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        if (customerService.insertCustomer(name))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JSONObject longinCustomer(HttpServletRequest request, String phone, String password) {
        HashMap map;
        try {
            map= customerService.findByPhone(phone).get(0);
            System.out.println(map);
        }catch (Exception e){
            return TipMap.accountError();
        }
        if (((String) map.get("password")).equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("cid", map.get("id"));
            session.setAttribute("name",map.get("name"));
            return TipMap.success();
        }
        return TipMap.passwordError();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public JSONObject updateCustomer(HttpServletRequest request, Customer customer) {
        HttpSession session=request.getSession();
        Integer cid=(Integer) session.getAttribute("cid");
        if(cid==null){
            Integer jid=root(request);
            if(jid==-1)
                return TipMap.loginError();
            if(jid==0)
                return TipMap.noRoot();
        }
        if (customerService.updateCustomer(customer))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "find", method = RequestMethod.GET)
    public JSONObject findCustomer(HttpServletRequest request, String name) {
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        if(jid==0)
            return TipMap.noRoot();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(TipMap.isSuccess, true);
        jsonObject.put(TipMap.results, customerService.findCustomers(name));
        return jsonObject;
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public JSONObject deleteCustomer(HttpServletRequest request, Integer id) {
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        if(jid==0)
            return TipMap.noRoot();
        try{
            customerService.deleteCustomer(id);
            return TipMap.success();
        }catch (Exception e){
            return TipMap.unknowError();
        }
    }
    @RequestMapping(value = "myself",method = RequestMethod.GET)
    public JSONObject myself(HttpServletRequest request){
        HttpSession session=request.getSession();
        String name=(String) session.getAttribute("name");
        Integer id=(Integer) session.getAttribute("cid");
        if(name==null)
            return TipMap.loginError();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(TipMap.isSuccess,true);
        String jsonString="{\"id\":"+id+",\"name\":\""+name+"\"}";
        jsonObject.put(TipMap.results,JSONObject.parseObject(jsonString));
        return jsonObject;
    }
}
