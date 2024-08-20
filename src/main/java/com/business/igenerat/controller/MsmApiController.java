package com.business.igenerat.controller;

import com.business.igenerat.services.EmployeeImpl;
import com.business.igenerat.services.SmsImpl;
import com.business.utils.RandomUtil;
import com.business.utils.SmsTimer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Eric
 * @create  2022-05-22 15:12
 */
@Api(tags = "阿里云短信服务")
@RestController
@RequestMapping("/msm")
public class MsmApiController {
    @Autowired
    private EmployeeImpl employeeService;

    @Autowired
    private SmsImpl smsSirvice;

    //发送短信验证码
    @ApiOperation(value = "发送短信验证码")
    @GetMapping(value = "/send")
    public Map<String,Object> code(String phone, HttpServletRequest request) {
        HttpSession session=request.getSession();

        //2、如果获取不到，就进行阿里云发送
        String code = RandomUtil.getFourBitRandom();//生成验证码的随机值
        Map<String,Object> param = new HashMap<>();
        param.put("code", code);

        //调用方法
        boolean isSend = smsSirvice.send(param,phone);
        if(isSend) {
            session.setAttribute("phone",phone);
            SmsTimer.setSmsCode(phone,code);//调用自定义计时器工具管理验证码
            return param;
        } else {
            return null;
        }
    }
}