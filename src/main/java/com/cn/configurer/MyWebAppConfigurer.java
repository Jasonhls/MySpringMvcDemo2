package com.cn.configurer;

import com.cn.handler.MyHandlerOne;
import com.cn.handler.MyHandlerTwo;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @description:
 * @author: helisen
 * @create: 2020-10-23 17:29
 **/
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyHandlerOne()).addPathPatterns("/abc");
        registry.addInterceptor(new MyHandlerTwo()).addPathPatterns("def");
    }
}
