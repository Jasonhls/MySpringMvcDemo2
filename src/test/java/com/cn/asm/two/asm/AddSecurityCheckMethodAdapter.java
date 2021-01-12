package com.cn.asm.two.asm;


import com.cn.asm.two.SecurityChecker;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-11 15:32
 **/
public class AddSecurityCheckMethodAdapter extends MethodVisitor {

    public AddSecurityCheckMethodAdapter(int api, MethodVisitor methodVisitor) {
        super(api, methodVisitor);
    }

    /**
     * ClassReader读到每个方法的首部时会调用visitCode()方法，在这个重写方法中，我们
     * 用visitMethodInsn(Opcodes.INVOKESTATIC , "SecurityChecker", "checkSecurity", ()V");    插入了安全检查功能的方法
     */
    public void visitCode() {
        visitMethodInsn(Opcodes.INVOKESTATIC, Type.getInternalName(SecurityChecker.class), "checkSecurity", "()V", false);
    }
}
