package com.cn.springxsd;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-12 09:25
 **/
public class StudentBeanDefinitionParser implements BeanDefinitionParser {

    private final Class<?> beanClass;

    public StudentBeanDefinitionParser(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        genericBeanDefinition.setBeanClass(beanClass);
        genericBeanDefinition.setLazyInit(false);
        genericBeanDefinition.getPropertyValues().add("id", element.getAttribute("id"));
        genericBeanDefinition.getPropertyValues().add("name", element.getAttribute("name"));
        parserContext.getRegistry().registerBeanDefinition(beanClass.getName(), genericBeanDefinition);
        return null;
    }
}
