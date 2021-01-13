package com.cn.asm.two.asm.subclass;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-12 17:14
 **/
public class ChangeToChildConstructorMethodVisitor extends MethodVisitor {

    private String superClassName;

    public ChangeToChildConstructorMethodVisitor(int api, MethodVisitor methodVisitor, String superClassName) {
        super(api, methodVisitor);
        this.superClassName = superClassName;
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface ) {
        //调用父类的构造函数时
        if(opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superClassName;
        }
        //改写父类为 superClassName
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }
}
