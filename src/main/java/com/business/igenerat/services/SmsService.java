package com.business.igenerat.services;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;

@Service
public interface SmsService {
    boolean send(Map<String, Object> param, String phone);
}
