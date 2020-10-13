package com.cn.config.otherPackage;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-13 17:32
 **/
@Configuration
public class MyCustomConfigSupport {
	@Bean
	public MyOtherBean myOtherBean() {
		MyOtherBean myOtherBean = new MyOtherBean();
		myOtherBean.setObject(new ArrayList<>());
		return myOtherBean;
	}
}
