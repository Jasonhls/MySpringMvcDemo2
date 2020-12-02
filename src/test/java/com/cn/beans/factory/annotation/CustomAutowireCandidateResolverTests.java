package com.cn.beans.factory.annotation;

import org.junit.Test;
import org.springframework.beans.factory.annotation.CustomAutowireConfigurer;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-25 13:54
 **/
public class CustomAutowireCandidateResolverTests {


    @Test
    public void test() {
        DefaultListableBeanFactory bf = new DefaultListableBeanFactory();
        new XmlBeanDefinitionReader(bf).loadBeanDefinitions("beans/CustomAutowireCandidateResolverTests.xml");
        CustomAutowireConfigurer configurer = new CustomAutowireConfigurer();
        CustomerAutowireCandidateResolver resolver = new CustomerAutowireCandidateResolver();
        bf.setAutowireCandidateResolver(resolver);
        configurer.postProcessBeanFactory(bf);
        TestBean testBean = (TestBean) bf.getBean("testBean");
        System.out.println(testBean.getName());
    }

    /**
     * 这里是内部类，需要加上static，如果不是内部类，就不需要加static
     */
    public static class TestBean {
        private String name;

        public TestBean(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    /**
     * 这里是内部类，需要加上static，如果不是内部类，就不需要加static
     */
    public static class CustomerAutowireCandidateResolver implements AutowireCandidateResolver {
        /**
         * 只能有一个java.lang.String类型的bean作为TestBean的构造方法中的参数，被注入到TestBean的属性name中
         * @param beanDefinitionHolder
         * @param dependencyDescriptor
         * @return
         */
        @Override
        public boolean isAutowireCandidate(BeanDefinitionHolder beanDefinitionHolder, DependencyDescriptor dependencyDescriptor) {
            //如果beanDefinition的属性autowireCandidate值为false，则返回false，不能被注入
            if(!beanDefinitionHolder.getBeanDefinition().isAutowireCandidate()) {
                return false;
            }
            //如果beanName中带有符号横杠，则返回false，也不能被注入
            if(beanDefinitionHolder.getBeanName().matches("[a-z-]+")) {
                return false;
            }

            if(beanDefinitionHolder.getBeanDefinition().getAttribute("priority").equals("1")) {
                return true;
            }
            return false;
        }

        @Override
        public Object getSuggestedValue(DependencyDescriptor dependencyDescriptor) {
            return null;
        }

        @Override
        public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor dependencyDescriptor, String s) {
            return null;
        }
    }
}
