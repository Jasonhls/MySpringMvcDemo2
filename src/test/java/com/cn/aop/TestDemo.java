package com.cn.aop;

import com.cn.utils.AesUtils;
import org.junit.Test;
import org.springframework.aop.framework.autoproxy.AutoProxyUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:59
 **/
public class TestDemo {
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("/aop/aspectTest.xml");
        TestBean testBean= (TestBean) context.getBean("test");
        testBean.test();
    }

    @Test
    public void test2() {
        Class<TestBean> beanClass = TestBean.class;
        String beanName = beanClass.getName();
        if (!StringUtils.hasLength(beanName) || beanName.length() !=
                beanClass.getName().length() + "&".length()) {
            System.out.println(false);
        }
         boolean b = (beanName.startsWith(beanClass.getName()) &&
                beanName.endsWith("&"));
        System.out.println(b);
    }

    @Test
    public void test3() throws Exception{
        String s = "pay_token=SYSTEM-DISTRIBUTOR-6eb5c4a2a66e40599824789f9985c0a9&pay_userId=4527391&pay_userName=13713974117&pay_order_no=FXS160879195752621936";
        String encrypt = AesUtils.encrypt(s);
        String encode = URLEncoder.encode(encrypt, "utf-8");
        //把换行符\r\n的编码值替换为空
        encode = encode.replaceAll(URLEncoder.encode("\r\n", "utf-8"), "");
        System.out.println("最终加密编码之后的结果：" + encode);

        String decode = URLDecoder.decode(encode, "utf-8");
        String decrypt = AesUtils.decrypt(decode);
        System.out.println("最终解码解码后的结果：" + decrypt);
    }
}
