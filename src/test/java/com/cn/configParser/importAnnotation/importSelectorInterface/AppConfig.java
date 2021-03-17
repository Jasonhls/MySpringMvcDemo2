package com.cn.configParser.importAnnotation.importSelectorInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @description:
 * @author: helisen
 * @create: 2021-03-17 14:30
 **/
@Configuration
//@PropertySource("classpath: importAnnotation/myapp.properties")
public class AppConfig {
    @Autowired
    private ConfigurableEnvironment environment;

    @Bean
    public ApplicationProperties applicationProperties() {
        ApplicationProperties bean = new ApplicationProperties();
//        bean.setConnectionUrl(environment.getProperty("app.url"));
        bean.setConnectionUrl("https://github.com/Jasonhls");
//        bean.setConnectName(environment.getProperty("app.name"));
        bean.setConnectName("Jasonhls");
        return bean;
    }
}
