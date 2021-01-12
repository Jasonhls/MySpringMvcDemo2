package com.cn.asm.one;

import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;
import org.springframework.asm.Opcodes;
import org.springframework.asm.Type;

/**
 * @description:
 * 执行命令：javap -s -c TesterTwo.class
 * Compiled from "TesterTwo.java"
 * public class com.cn.asm.one.TesterTwo {
 *   public com.cn.asm.one.TesterTwo();
 *     descriptor: ()V
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *
 *   public java.lang.Integer getIntVal();
 *     descriptor: ()Ljava/lang/Integer;
 *     Code:
 *        0: bipush        10
 *        2: invokestatic  #2                  // Method java/lang/Integer.valueOf:(I)Ljava/lang/Integer;
 *        5: areturn
 * }
 * @author: helisen
 * @create: 2021-01-11 14:06
 **/
public class AsmGettingStartedTwo {
    static ClassWriter createClassWriter(String className) {
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //声明一个类，使用JDK1.8版本，public的类，父类是java.lang.Object，没有实现任何接口
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, className, null, Type.getInternalName(Object.class), null);
        //初始化一个无参的构造方法
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        //这里请看类上的description
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        //执行父类的init初始化
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, Type.getInternalName(Object.class), "<init>", "()V", false);
        //从当前方法返回void
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();
        return cw;
    }

    static byte[] createReturnMethod(String className, int returnValue) throws Exception {
        ClassWriter cw = createClassWriter(className.replace(".", "/"));
        MethodVisitor getIntValMethod = cw.visitMethod(Opcodes.ACC_PUBLIC, "getIntVal", "()Ljava/lang/Integer;", null, null);
        //将单字节的常量值（-128~127）推送至栈顶（如果不是-128~127之间的数字，则不能用bipush指令）
        getIntValMethod.visitIntInsn(Opcodes.BIPUSH, returnValue);
        //调用Integer的静态方法valueOf把10转换成Integer对象
        //获取方法的描述值
        String methodDesc = Type.getMethodDescriptor(Integer.class.getMethod("valueOf", int.class));
        System.out.println(methodDesc);//(I)Ljava/lang/Integer;  括号里面的是大写的i
        getIntValMethod.visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(Integer.class), "valueOf", methodDesc);
        //从当前方法返回对象引用
        getIntValMethod.visitInsn(Opcodes.ARETURN);
        getIntValMethod.visitMaxs(1, 1);
        getIntValMethod.visitEnd();

        return cw.toByteArray();
    }

    public static void main(String[] args) throws Exception{
        String className = "com.cn.asm.one.TestTwo";
        byte[] classData = createReturnMethod(className, 10);
        Class<?> clazz = new MyClassLoader().defineClassForName(className, classData);
        Object value = clazz.getMethods()[0].invoke(clazz.newInstance());
        System.out.println(value);
    }
}
