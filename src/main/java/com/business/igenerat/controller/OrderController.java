package com.business.igenerat.controller;

import com.alibaba.fastjson.JSONObject;
import com.business.igenerat.pojo.Order;
import com.business.igenerat.services.OrderImpl;
import com.business.igenerat.services.ProductImpl;
import com.business.utils.Root;
import com.business.utils.TipMap;
import org.omg.CORBA.TIMEOUT;
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
@RequestMapping("/order")
@Root(roots = {1,2,4})
public class OrderController {
    @Autowired
    ProductImpl productService;

    @Autowired
    OrderImpl orderService;
    private int root(HttpServletRequest request){//权限过滤器
        Integer jid=(Integer)request.getSession().getAttribute("jid");
        if (jid==null)
            return -1;
        for(int it: OrderController.class.getAnnotation(Root.class).roots()){
            if(jid==it)
                return jid;
        }
        return 0;
    }
    static public Integer cRoot(HttpServletRequest request){
        HttpSession session=request.getSession();
        Integer cid=(Integer) session.getAttribute("cid");
        return cid;
    }
    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    public JSONObject insertOrder(HttpServletRequest request, Order order, Integer pid, Integer cid, Integer eid){
        Integer jid=root(request);
        if(cRoot(request)==null){
            if(jid==-1)
                return TipMap.loginError();
            if(jid==0)
                return TipMap.noRoot();
        }
        HttpSession session=request.getSession();
        if(eid==null)
            eid=(Integer) session.getAttribute("id");
        if(order.getDelivery()){
            if(!productService.addProduct(pid,-order.getNumber()))
                return TipMap.wrong(TipMap.insufficientInventory);
        }
        if(orderService.insertOrder(order,pid,cid,eid))
            return TipMap.success();
        return TipMap.unknowError();
    }

    @RequestMapping(value = "/find",method = RequestMethod.GET)
    public JSONObject findOrder(HttpServletRequest request,String pname,String cname){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        else if(jid==0)
            return TipMap.noRoot();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(TipMap.isSuccess,true);
        jsonObject.put(TipMap.results,orderService.findOrder(pname,cname));
        return jsonObject;
    }

    @RequestMapping(value = "/findMyOrder",method = RequestMethod.GET)
    public JSONObject findMyOrder(HttpServletRequest request){
        Integer cid=cRoot(request);
        if (cid==null)
            return TipMap.loginError();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(TipMap.isSuccess,true);
        jsonObject.put(TipMap.results,orderService.findMyOrder(cid));
        return jsonObject;
    }
    @RequestMapping(value = "/deleteMyOrder",method = RequestMethod.POST)
    public JSONObject deleteMyOrder(HttpServletRequest request,Integer id){
        Integer cid=cRoot(request);
        if (cid==null)
            return TipMap.loginError();
        HashMap map=orderService.findOrderById(id).get(0);
        Boolean delivery=(Boolean)map.get("delivery");
        Boolean payment=(Boolean)map.get("payment");
        if(delivery==payment){
            if(orderService.deleteOrder(id))
                return TipMap.success();
            return TipMap.unknowError();
        }
        return TipMap.unknowError();

    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public JSONObject updateOrder(HttpServletRequest request,Order order,Integer pid,Integer cid){
        Integer jid=root(request);
        if(cRoot(request)==null){
            if(jid==-1)
                return TipMap.loginError();
            if(jid==0)
                return TipMap.noRoot();
        }
        HashMap map=orderService.findOrderById(order.getId()).get(0);
        if(map==null)
            return null;
        if((Boolean)map.get("delivery")||(Boolean)map.get("payment"))
            return null;
        if(orderService.updateOrder(order,pid,cid))
            return TipMap.success();
        return TipMap.unknowError();
    }
    @RequestMapping(value = "/upstate",method = RequestMethod.POST)
    public JSONObject updateState(HttpServletRequest request,Order order){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        if(jid==0)
            return TipMap.noRoot();
        HashMap map=orderService.findOrderById(order.getId()).get(0);
        if(map==null)
            return null;
        Boolean delivery=(Boolean)map.get("delivery"),payment=(Boolean)map.get("payment");
        if((delivery&&!order.getDelivery())||(payment&&!order.getPayment())){
            return null;
        }
        if((delivery==order.getDelivery())&&(payment==order.getPayment()))
            return null;
        if(!delivery&&order.getDelivery()){
            if(!productService.addProduct((Integer)map.get("pid"),-(Integer)map.get("number")))
                return TipMap.wrong(TipMap.insufficientInventory);
        }
        orderService.updateOrder(order,null,null);
        return TipMap.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JSONObject deleteOrder(HttpServletRequest request,Integer id){
        Integer jid=root(request);
        if(jid==-1)
            return TipMap.loginError();
        if(jid==0)
            return TipMap.noRoot();
        HashMap map=orderService.findOrderById(id).get(0);
        Boolean delivery=(Boolean)map.get("delivery");
        Boolean payment=(Boolean)map.get("payment");
        if(delivery&&!payment){
            productService.addProduct((Integer)map.get("pid"),(Integer)map.get("number"));
        }
        orderService.deleteOrder(id);
        return TipMap.success();
    }
}
