package com.cn.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * @description:
 * @author: helisen
 * @create: 2020-07-23 13:55
 **/
public class WebXml extends AbstractAnnotationConfigDispatcherServletInitializer {
    /**
     <context-param>
     <param-name>contextConfigLocation</param-name>
     <param-value>/WEB-INF/applicationContext.xml</param-value>
     </context-param>
     <listener>
     <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
     </listener>
     * @return
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{ApplicationContextXml.class};
    }

    /**
     <!--配置springmvc DispatcherServlet-->
     <servlet>
     <servlet-name>springMVC</servlet-name>
     <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
     <init-param>
     <!--配置dispatcher.xml作为mvc的配置文件-->
     <param-name>contextConfigLocation</param-name>
     <param-value>/WEB-INF/springMVC-servlet.xml</param-value>
     </init-param>
     <load-on-startup>1</load-on-startup>
     <async-supported>true</async-supported>
     </servlet>
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{DispatcherServletXml.class};
    }

    /**
     <servlet-mapping>
     <servlet-name>springMVC</servlet-name>
     <url-pattern>/</url-pattern>
     </servlet-mapping>
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
