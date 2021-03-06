package com.cn.rpc.bean;

import java.io.Serializable;

/**
 * @description:
 * @author: helisen
 * @create: 2020-11-17 09:53
 **/
public class RpcRequest implements Serializable {

    private static final long serialVersionUID = -8957512876158281449L;

    private String className;
    private String methodName;
    private Class<?>[] types;
    private Object[] params;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public void setTypes(Class<?>[] types) {
        this.types = types;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
