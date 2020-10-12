package com.cn.servletContextListener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @description: 自定义的ServletContextListener，tomcat启动的时候会执行ServletContextListener的contextInitialized(ServletContextEvent sce)方法
 * @author: helisen
 * @create: 2020-10-12 11:13
 **/
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("开始初始化");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("销毁");
    }
}
