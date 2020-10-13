package com.cn.config.otherPackage;

import org.springframework.beans.factory.InitializingBean;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-13 17:33
 **/
public class MyOtherBean implements InitializingBean {

	private String name;
	private Object object;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.name = "hls";
	}
}
