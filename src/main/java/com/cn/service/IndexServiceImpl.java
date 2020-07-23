package com.cn.service;

import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-21 13:35
 **/
@Service
public class IndexServiceImpl implements IndexService {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
