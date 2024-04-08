package com.cn.aop.xml;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-04 14:01
 **/
public class JdkServiceImpl implements JdkService {
    @Override
    public String test(String way) {
        String result = "this is the way : " + way;
        System.out.println(result);
        return result;
    }
}