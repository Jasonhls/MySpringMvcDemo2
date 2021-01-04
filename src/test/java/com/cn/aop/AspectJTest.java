package com.cn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-28 10:47
 **/
@Aspect
public class AspectJTest {

    //拦截所有的test方法
    @Pointcut("execution(* *.test(..))")
    public void test() {

    }

    @Before("test()")
    public void beforeTest() {
        System.out.println("beforeTest");
    }

    @After("test()")
    public void afterTest() {
        System.out.println("afterTest");
    }

    /**
     * 运行结果：
     *      before1
     *      beforeTest
     *      test
     *      after1
     *      afterTest
     * @param p
     * @return
     */
    @Around("test()")
    public Object aroundTest(ProceedingJoinPoint p) {
        System.out.println("before1");
        Object result = null;
        try {
            result = p.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        System.out.println("after1");
        return result;
    }
}
