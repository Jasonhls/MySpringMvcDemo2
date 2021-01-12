package com.cn.asm.one;

import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;

/**
 * @description:  通过cmd中输入命令：javap -s -c TestOne.class，可以查看class的文件格式(部分)如下：
 *Compiled from "TesterOne.java"
 * public class com.cn.asm.one.TesterOne {
 *   public com.cn.asm.one.TesterOne();
 *     descriptor: ()V
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public void run();
 *     descriptor: ()V
 *     Code:
 *        0: getstatic     #2                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *        3: ldc           #3                  // String this is my first asm test
 *        5: invokevirtual #4                  // Method java/io/PrintStream.println:(Ljava/lang/String;)V
 *        8: return
 * }
 * @author: helisen
 * @create: 2021-01-11 11:05
 **/
public class AsmGettingStarted {
    static ClassWriter createClassWriter(String className) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //声明一个类，使用JDK1.8版本，public的类，父类是java.lang.Object, 没有实现任何接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, "java/lang/Object", null);
        //初始化一个无参的构造方法
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);

        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        //执行父类的init初始化
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        //从当前返回void
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
        return cw;
    }

    static byte[] createVoidMethod(String className, String message) {
        ClassWriter cw = createClassWriter(className.replace(".", "/"));
        //()v表示函数，无参数，无返回值
        MethodVisitor runMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "run", "()V", null, null);
        //先获取一个java.io.PrintStream对象
        runMethod.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        //将int, float或String类型常量值从常量池中推送至栈顶（此处将message字符串从常量池中推送到栈顶[输出的内容]）
        runMethod.visitLdcInsn(message);
        //执行println方法（执行的是参数为字符串，无返回值的println函数）
        runMethod.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        runMethod.visitInsn(Opcodes.RETURN);
        runMethod.visitMaxs(1, 1);
        runMethod.visitEnd();

        return cw.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        String className = "com.cn.asm.one.Tester";
        byte[] classData = createVoidMethod(className, "this is my first asm test");
        Class<?> clazz = new MyClassLoader().defineClassForName(className, classData);
        clazz.getMethods()[0].invoke(clazz.newInstance());
    }
}
