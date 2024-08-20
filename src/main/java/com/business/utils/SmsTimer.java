package com.business.utils;

import java.util.*;

public class SmsTimer extends TimerTask {//队列控制器管理验证码创建与过时删除
    private static Map<String,String> smsCode=new HashMap<>();
    private static Queue<Date> smsDate=new LinkedList<>();
    private static Timer smsTimer;
    private static void startTimer(){//一直运行直到队列为空
        if(smsDate.isEmpty()) return;
        smsTimer=new Timer();
        smsTimer.schedule(new SmsTimer(),smsDate.poll());
    }

    public static void setSmsCode(String phone,String code) {//由于入队值为入队时间+60000毫秒队列必定为时间升序
        smsCode.put(phone,code);
        smsDate.add(new Date(System.currentTimeMillis()+60000));
        if(smsDate.size()==1){
            startTimer();
        }
    }

    public static void delSmsCode(String phone){
        smsCode.remove(phone);
    }

    public static String getSmsCode(String phone) {
        return smsCode.get(phone);
    }

    @Override
    public void run() {
        startTimer();
    }
}
