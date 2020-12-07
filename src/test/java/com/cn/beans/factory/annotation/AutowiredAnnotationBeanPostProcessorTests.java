package com.cn.beans.factory.annotation;

import com.cn.test.simple.beans.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-25 17:31
 **/
public class AutowiredAnnotationBeanPostProcessorTests {
    private DefaultListableBeanFactory bf;
    private AutowiredAnnotationBeanPostProcessor bff;

    @Before
    public void init() {
        this.bf = new DefaultListableBeanFactory();
        bf.registerResolvableDependency(BeanFactory.class, bf);
        bff = new AutowiredAnnotationBeanPostProcessor();
        bff.setBeanFactory(bf);
        bf.addBeanPostProcessor(bff);
        bf.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());
        bf.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE);
    }

    @After
    public void close() {
        bf.destroySingletons();
    }

    @Test
    public void test() {
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(MyBean.class);
        rootBeanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        bf.registerBeanDefinition("myBean", rootBeanDefinition);
        /*TestBean tb = new TestBean();
        bf.registerSingleton("testBean", tb);*/
        bf.registerBeanDefinition("testBean", new RootBeanDefinition(TestBean.class));
        //上面没有指定testBean的scope，因此下面getBean("myBean")的时候，由于注入了TestBean，因此会先创建单例TestBean
        MyBean bean = (MyBean) bf.getBean("myBean");
        Assert.assertSame(bean.getTestBean2(), bean.getTestBean3());
        Assert.assertSame(bean.getTestBean2(), bean.getTestBean4());

    }

    @Test
    public void test2() {
        RootBeanDefinition r = new RootBeanDefinition(MyTypedExtendedResourceInjectionBean.class);
        r.setScope(BeanDefinition.SCOPE_PROTOTYPE);
        Method[] declaredMethods = r.getBeanClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method.getName());
        }
    }

    @Test
    public void test3() throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Student.class);
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor pd : propertyDescriptors) {
            Method writeMethod = pd.getWriteMethod();
            Method readMethod = pd.getReadMethod();
        }
    }

    @Test
    public void test4() {
        Constructor<?>[] declaredConstructors = ConstructorsResourceInjectionBean.class.getDeclaredConstructors();
        for (Constructor c : declaredConstructors) {
            System.out.println(c.getName());
        }
    }



    public static class ConstructorsResourceInjectionBean {

        protected ITestBean testBean3;

        private ITestBean testBean4;

        private NestedTestBean[] nestedTestBeans;

        public ConstructorsResourceInjectionBean() {
        }

        @Autowired(required = false)
        public ConstructorsResourceInjectionBean(ITestBean testBean4, NestedTestBean[] nestedTestBeans) {
            this.testBean4 = testBean4;
            this.nestedTestBeans = nestedTestBeans;
        }

        @Autowired(required = false)
        public ConstructorsResourceInjectionBean(ITestBean testBean3) {
            this.testBean3 = testBean3;
        }

        public ConstructorsResourceInjectionBean(NestedTestBean nestedTestBean) {
            throw new UnsupportedOperationException();
        }

        public ConstructorsResourceInjectionBean(ITestBean testBean3, ITestBean testBean4, NestedTestBean nestedTestBean) {
            throw new UnsupportedOperationException();
        }

        public ITestBean getTestBean3() {
            return this.testBean3;
        }

        public ITestBean getTestBean4() {
            return this.testBean4;
        }

        public NestedTestBean[] getNestedTestBeans() {
            return this.nestedTestBeans;
        }
    }


    public static class MyResourceInjectionBean {
        @Autowired(required = false)
        private TestBean testBean;

        private TestBean testBean2;

        @Autowired
        public void setTestBean2(TestBean testBean2) {
            if (this.testBean2 != null) {
                throw new IllegalStateException("Already called");
            }
            this.testBean2 = testBean2;
        }

        public TestBean getTestBean() {
            return this.testBean;
        }

        public TestBean getTestBean2() {
            return this.testBean2;
        }
    }

    /**
     * 下面这个类没有加public关键字，所以子类getDeclaredMethods()，也会获取这个类的公共方法(排除返回值为泛型的方法)，私有方法和protected方法还是不能获取
     * @param <T>
     */
    static class MyNonPublicResourceInjectionBean<T> extends MyResourceInjectionBean {
        //通过属性注入
        @Autowired
        public final ITestBean testBean3 = null;

        private T nestedTestBean;

        private ITestBean testBean4;

        protected BeanFactory beanFactory;

        public boolean baseInjected = false;

        public MyNonPublicResourceInjectionBean() {
        }

        @Override
        @Autowired @Required
        public void setTestBean2(TestBean testBean2) {
            super.setTestBean2(testBean2);
        }

        //通过方法注入
        @Autowired
        private void inject(ITestBean testBean4, T nestedTestBean) {
            this.testBean4 = testBean4;
            this.nestedTestBean = nestedTestBean;
        }

        @Autowired
        private void inject(ITestBean testBean4) {
            this.baseInjected = true;
        }

        @Autowired
        protected void initBeanFactory(BeanFactory beanFactory) {
            this.beanFactory = beanFactory;
        }

        protected String say() {
            return "hello";
        }

        private String say2() {
            return "hello2";
        }

        public ITestBean getTestBean3() {
            return this.testBean3;
        }

        public ITestBean getTestBean4() {
            return this.testBean4;
        }

        public T getNestedTestBean() {
            return this.nestedTestBean;
        }

        public BeanFactory getBeanFactory() {
            return this.beanFactory;
        }
    }

    public static  class MyTypedExtendedResourceInjectionBean extends  MyNonPublicResourceInjectionBean<TestBean> implements DisposableBean {
        public boolean destroyed = false;
        @Override
        public void destroy() throws Exception {
            this.destroyed = true;
        }
    }
}
