package com.cn.springxsd;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-12 09:29
 **/
public class StudentNamespaceHandlerSupport extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("student", new StudentBeanDefinitionParser(Student.class));
    }
}
