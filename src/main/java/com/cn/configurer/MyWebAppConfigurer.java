package com.cn.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.cn.handler.MyHandlerOne;
import com.cn.handler.MyHandlerTwo;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        List<MediaType> supportMediaTypeList = new ArrayList<>();
        supportMediaTypeList.add(MediaType.APPLICATION_JSON_UTF8);

        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue, //保留空的字段
                SerializerFeature.WriteNullStringAsEmpty, // String null -> ""
                SerializerFeature.WriteNullNumberAsZero, // Number null -> 0
                SerializerFeature.WriteNullListAsEmpty, // List null -> []
                SerializerFeature.WriteNullBooleanAsFalse // Boolean null -> false
        );
        converter.setFastJsonConfig(config);
        converter.setSupportedMediaTypes(supportMediaTypeList);
        converter.setCharset(Charset.forName("UTF-8"));
        converters.add(converter);
    }
}
