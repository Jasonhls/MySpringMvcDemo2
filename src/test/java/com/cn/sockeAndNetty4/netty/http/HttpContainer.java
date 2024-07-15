package com.cn.sockeAndNetty4.netty.http;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 何立森
 * @Date: 2024/07/15/9:09
 * @Description: 处理请求的容器
 */
public class HttpContainer {
    private final static Logger logger = LoggerFactory.getLogger(HttpContainer.class);

    public static final List<RequestHandler> handlerList = new ArrayList<>(8);

    private String[] backPackages;

    public HttpContainer(String[] backPackages) {
        this.backPackages = backPackages;
    }

    public void init() {
        Set<Class<?>> allClasses = Arrays.stream(backPackages).map(HttpContainer::scanClass).flatMap(Collection::stream).collect(Collectors.toSet());
        //遍历自定义接口路由
        Map<Class<?>, Object> cache = new HashMap<>();
        for (Class<?> aClass : allClasses) {
            Method[] declaredMethods = aClass.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if(declaredMethod.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = declaredMethod.getAnnotation(RequestMapping.class);

                    RequestHandler requestHandler = new RequestHandler();
                    String[] value = annotation.value();
                    String path = value[0];
                    requestHandler.setUr(path);

                    RequestMethod[] method = annotation.method();
                    RequestMethod requestMethod = RequestMethod.GET;
                    if(method.length > 0) {
                        requestMethod = method[0];
                    }
                    requestHandler.setRequestMethod(requestMethod);

                    requestHandler.setMethod(declaredMethod);

                    try {
                        Object object;
                        if(!cache.containsKey(aClass)) {
                            Constructor<?> constructor = aClass.getConstructor(null);
                            object = constructor.newInstance(null);
                            cache.put(aClass, object);
                        }else {
                            object = cache.get(aClass);
                        }
                        requestHandler.setObject(object);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    handlerList.add(requestHandler);
                }
            }
        }
        logger.info("http容器启动成功！");
    }


    /**
     * 扫描包下面所有的类
     * @param backPackage
     * @return
     */
    public static List<Class<?>> scanClass(String backPackage) {
        List<Class<?>> list = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url = classLoader.getResource(backPackage.replaceAll("\\.", "/"));
        if(url == null) {
            throw new RuntimeException("文件不存在");
        }
        File file = new File(url.getFile());
        for (File f : Objects.requireNonNull(file.listFiles())) {
            if(f.getName().endsWith(".class")) {
                String name = f.getName();
                Class<?> aClass;
                try {
                    aClass = Class.forName(backPackage + "." + name.substring(0, name.lastIndexOf(".")));
                    list.add(aClass);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return list;
    }


    public static RequestHandler getHandler(String url, String methodName) {
        List<RequestHandler> collect = handlerList.stream().filter(a -> Objects.equals(a.getUr(), url)
                && Objects.equals(a.getRequestMethod().name(), methodName)).collect(Collectors.toList());
        if(collect.size() > 0) {
            return collect.get(0);
        }
        throw new RuntimeException("找不到handler");
    }

    public static Object[] handleRequestParam(FullHttpRequest fullHttpRequest, Method method) {
        Parameter[] parameters = method.getParameters();
        Object[] result = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            //如果参数为RequestBody类型
            if(parameter.isAnnotationPresent(RequestBody.class)) {
                Class<?> paramType = parameter.getType();
                ByteBuf byteBuf = fullHttpRequest.content();
                String content = byteBuf.toString(StandardCharsets.UTF_8);
                result[i] = JSON.parseObject(content, paramType);
            }else if(parameter.isAnnotationPresent(RequestParam.class)) {
                //localhost:7397/student/getInfo?accountId=2150088&name=张三
                QueryStringDecoder queryStringDecoder = new QueryStringDecoder(fullHttpRequest.uri());
                Map<String, List<String>> stringListMap = queryStringDecoder.parameters();

                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String name = requestParam.name();
                List<String> values = stringListMap.get(name);
                if(values.size() > 0) {
                    Class<?> paramType = parameter.getType();
                    if(Objects.equals(paramType, Long.class)) {
                        result[i] = Long.parseLong(values.get(0));
                    }else if(Objects.equals(paramType, String.class)) {
                        result[i] = values.get(0);
                    }else if(Objects.equals(paramType, Integer.class)) {
                        result[i] = Integer.parseInt(values.get(0));
                    }
                }
            }
        }
        logger.info("解析入参结果：{}", JSON.toJSONString(result));
        return result;
    }





    public class RequestHandler {
        private String ur;
        private RequestMethod requestMethod;
        private Method method;
        private Object object;

        public RequestHandler() {
        }

        public RequestHandler(String ur, RequestMethod requestMethod, Method method, Object object) {
            this.ur = ur;
            this.requestMethod = requestMethod;
            this.method = method;
            this.object = object;
        }

        public String getUr() {
            return ur;
        }

        public void setUr(String ur) {
            this.ur = ur;
        }

        public RequestMethod getRequestMethod() {
            return requestMethod;
        }

        public void setRequestMethod(RequestMethod requestMethod) {
            this.requestMethod = requestMethod;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }



}
