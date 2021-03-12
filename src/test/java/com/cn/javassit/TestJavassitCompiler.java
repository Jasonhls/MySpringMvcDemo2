package com.cn.javassit;


import javassist.*;

import java.lang.reflect.Method;


/**
 * @description:
 * @author: helisen
 * @create: 2021-03-12 12:24
 **/
public class TestJavassitCompiler {
    public static void main(String[] args) throws Exception{
        ClassPool pool = ClassPool.getDefault();
        //1、创建一个空类
        CtClass cc = pool.makeClass("com.cn.javassit.Student");

        //2、新增一个字段：private String name
        CtField sex = new CtField(pool.get("java.lang.String"), "sex", cc);
        sex.setModifiers(Modifier.PRIVATE);
        //初始值为man
        cc.addField(sex, CtField.Initializer.constant("man"));

        //添加属性：private int age
        CtField age = new CtField(pool.get("int"), "age", cc);
        age.setModifiers(Modifier.PRIVATE);
        cc.addField(age, CtField.Initializer.constant(20));

        //3、生成getter、setter方法
        cc.addMethod(CtNewMethod.getter("getSex", sex));
        cc.addMethod(CtNewMethod.setter("setSex", sex));
        cc.addMethod(CtNewMethod.getter("getAge", age));
        cc.addMethod(CtNewMethod.setter("setAge", age));

        //4、创建无参的构造方法
        CtConstructor cons = new CtConstructor(new CtClass[] {}, cc);
        //注意：这里的body中必须拼接大括号{}，这样才能生成方法体
        cons.setBody("{}");
        cc.addConstructor(cons);

        //5、添加有参的构造方法
        cons = new CtConstructor(new CtClass[]{pool.get("java.lang.String"), pool.get("int")}, cc);
        //$0表示this，$1表示第一个参数，$2表示第二个参数
        cons.setBody(new StringBuilder("{$0.sex = $1;$0.age = $2;}").toString());
        cc.addConstructor(cons);

        //6、普通方法，没有入参和返回值
        CtMethod ctMethod = new CtMethod(CtClass.voidType, "commonMethod", new CtClass[] {}, cc);
        ctMethod.setModifiers(Modifier.PUBLIC);
        ctMethod.setBody(new StringBuilder("{System.out.println(sex);\nSystem.out.println(age);}").toString());
        cc.addMethod(ctMethod);

        //增加普通方法，有入参和出参
        CtMethod sayHello = new CtMethod(CtClass.intType, "sayHello", new CtClass[]{pool.get("java.lang.String")}, cc);
        sayHello.setModifiers(Modifier.PUBLIC);
        sayHello.setBody(new StringBuilder("{System.out.println($1);\nreturn age;}").toString());
        cc.addMethod(sayHello);


//        cc.writeFile("E:\\my_project\\MySpringMvcDemo2\\src\\test\\java\\");

        Object obj = cc.toClass().newInstance();
        //调用commonMethod方法
        Method method1 = obj.getClass().getMethod("commonMethod");
        //commonMethod方法没有入参
        method1.invoke(obj);
        System.out.println("-----------------------");

        //根据方法名和入参类型去查找方法
        Method sayHello1 = obj.getClass().getMethod("sayHello", String.class);
        int result = (int) sayHello1.invoke(obj, "haha");
        System.out.println(result);
        System.out.println("-----------------------");


        //调用set方法设置属性sex的值
        Method setSex = obj.getClass().getMethod("setSex", String.class);
        setSex.invoke(obj, "haha");
        //调用set方法设置属性age的值
        Method setAge = obj.getClass().getMethod("setAge", int.class);
        setAge.invoke(obj, 32);

        //修改属性值之后再调用commonMethod方法
        Method method2 = obj.getClass().getMethod("commonMethod");
        method2.invoke(obj);
    }
}
