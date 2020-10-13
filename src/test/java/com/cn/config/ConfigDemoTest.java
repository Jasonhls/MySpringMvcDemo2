package com.cn.config;

import com.cn.config.demo.MyConfig;
import com.cn.config.demo.MyStudent;
import com.cn.config.otherPackage.MyOtherBean;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-13 17:28
 **/
public class ConfigDemoTest {
	@Test
	public void testConfigDemo() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
		MyStudent bean = context.getBean(MyStudent.class);
		System.out.println(bean.getName());
		MyOtherBean bean1 = context.getBean(MyOtherBean.class);
		System.out.println(bean1.getName());
	}
}
