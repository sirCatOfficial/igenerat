package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Order;
import com.business.igenerat.services.MaterialService;
import com.business.igenerat.services.POrderImpl;
import com.business.igenerat.services.POrderService;
import com.business.utils.Root;
import com.business.utils.TipMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/porder")
@Root(roots = {1,2,5})
public class POrderController {
    @Autowired
    private POrderImpl pOrderService;
    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: POrderController.class.getAnnotation(Root.class).roots()){
            if(jid==it)
                return jid;
        }
        return 0;
    }
    @Autowired
    MaterialService materialService;

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject searchPOrders(HttpServletRequest request,String sname,String mname,Integer id) {
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        return TipMap.successFind(pOrderService.selectPOrderByConditions(sname, mname, id));
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject updateOrder(HttpServletRequest request, Order order, Integer mid, Integer sid){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        HashMap map=pOrderService.findPOrderById(order.getId()).get(0);
        if(map==null)
            return null;
        if((Boolean)map.get("delivery")||(Boolean)map.get("payment"))
            return null;
        if(pOrderService.updatePOrder(order,mid,sid))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteOrder(HttpServletRequest request,Integer id){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        HashMap map=pOrderService.findPOrderById(id).get(0);
        Boolean delivery=(Boolean)map.get("delivery");
        Boolean payment=(Boolean)map.get("payment");
        if(delivery&&!payment){
            materialService.addMaterial((Integer)map.get("mid"),-(Integer)map.get("number"));
        }
        pOrderService.deletePOrder(id);
        return TipMap.success();
    }
    @RequestMapping(value = "/upstate",method = RequestMethod.POST)
    public JSONObject updateState(HttpServletRequest request,Order order){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        HashMap map=pOrderService.findPOrderById(order.getId()).get(0);
        if(map==null)
            return TipMap.unknowError();
        Boolean delivery=(Boolean)map.get("delivery"),payment=(Boolean)map.get("payment");
        if((delivery&&!order.getDelivery())||(payment&&!order.getPayment())){
            return null;
        }
        if((delivery==order.getDelivery())&&(payment==order.getPayment()))
            return null;
        if(!delivery&&order.getDelivery()){
            materialService.addMaterial((Integer)map.get("mid"),(Integer)map.get("number"));
        }
        pOrderService.updatePOrder(order,null,null);
        return TipMap.success();
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONObject insertOrder(HttpServletRequest request, Order order, Integer mid, Integer sid, Integer eid){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if (jid==0)
            return TipMap.noRoot();
        HttpSession session=request.getSession();
        if(eid==null)
            eid=(Integer) session.getAttribute("id");
        if(order.getDelivery()){
            materialService.addMaterial(mid,order.getNumber());
        }
        if(pOrderService.insertPOrder(mid,sid,eid,order))
            return TipMap.success();
        return TipMap.unknowError();
    }
}
