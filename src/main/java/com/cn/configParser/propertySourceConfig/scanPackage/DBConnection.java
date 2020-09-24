package com.cn.configParser.propertySourceConfig.scanPackage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: helisen
 * @create: 2020-09-24 15:58
 **/
@Component
@PropertySource(value = {"/properties/db.properties"})
public class DBConnection {
    @Value("${DB_DRIVER_CLASS}")
    private String driverClass;

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_USERNAME}")
    private String userName;

    @Value("${DB_PASSWORD}")
    private String password;

    public DBConnection() {
    }

    public void printDBConfigs() {
        System.out.println("Db Driver Class = " + driverClass);
        System.out.println("Db url = " + dbUrl);
        System.out.println("Db userName = " + userName);
        System.out.println("Db password = " + password);
    }
}
