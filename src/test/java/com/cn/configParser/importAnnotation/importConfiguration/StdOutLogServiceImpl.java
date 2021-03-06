package com.cn.configParser.importAnnotation.importConfiguration;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:09
 **/
@Component
public class StdOutLogServiceImpl implements LogService{
    @Override
    public void print(String message) {
        System.out.println("写日志到控制台！" + message);
    }
}
