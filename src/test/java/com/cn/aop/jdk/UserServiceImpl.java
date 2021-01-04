package com.cn.aop.jdk;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-31 11:19
 **/
public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("---------add------------");
    }
}
