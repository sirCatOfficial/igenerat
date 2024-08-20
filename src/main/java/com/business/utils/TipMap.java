package com.business.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.List;

public class TipMap {
    public static String isSuccess="isSuccess";
    public static String results="results";
    public static String accountError="手机号错误";
    public static String passwordError="密码错误";
    public static String loginError="登录错误";
    public static String success="成功";
    public static String reRegister="已注册";
    public static String errorId="员工号错误";
    public static String unknowError="未知错误";
    public static String noRoot="权限不足";
    public static String timeLimit="验证超时";
    public static String codeError="验证码错误";
    public static String certError="证书错误,请重新获取验证码或检查手机号";
    public static String insufficientInventory="库存不足";

    public static JSONObject loginError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,loginError);
        return jsonObject;
    }
    public static JSONObject noRoot(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,noRoot);
        return jsonObject;
    }
    public static JSONObject success(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,true);
        jsonObject.put(results,success);
        return jsonObject;
    }
    public static JSONObject unknowError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,unknowError);
        return jsonObject;
    }
    public static JSONObject timeLimit(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,timeLimit);
        return jsonObject;
    }
    public static JSONObject codeError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,codeError);
        return jsonObject;
    }
    public static JSONObject certError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,certError);
        return jsonObject;
    }
    public static JSONObject reRegister(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,reRegister);
        return jsonObject;
    }
    public static JSONObject accountError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,accountError);
        return jsonObject;
    }
    public static JSONObject passwordError(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,passwordError);
        return jsonObject;
    }
    public static JSONObject successFind(List<HashMap> list){
        JSONObject jsonObject =new JSONObject();
        jsonObject.put(isSuccess,true);
        jsonObject.put(results,list);
        return jsonObject;
    }
    public static JSONObject wrong(String reason){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put(isSuccess,false);
        jsonObject.put(results,reason);
        return jsonObject;
    }
}
