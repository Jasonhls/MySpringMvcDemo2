package com.cn.configParser.importAnnotation.importBeanDefinitionRegistrarInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 15:51
 **/
@CustomComponent
public class CustomDataMapper {
    public List printData() {
        List<String> list = new ArrayList<>();
        list.add("hls");
        list.add("张三");
        list.add("李四");
        return list;
    }
}
