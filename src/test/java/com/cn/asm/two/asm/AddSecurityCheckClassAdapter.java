package com.cn.asm.two.asm;


import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-11 15:17
 **/
public class AddSecurityCheckClassAdapter extends ClassVisitor {
    public AddSecurityCheckClassAdapter(int api, ClassVisitor classVisitor) {
        //ResponseChain 的下一个ClassVisitor，这里我们将插入ClassVisitor
        //赋值改写后代码的输出
        super(api, classVisitor);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if(mv != null) {
            //对于"operation"方法
            if(name.equals("operation")) {
                // 使用自定义MethodVisitor，实际改写方法内容
                wrappedMv = new AddSecurityCheckMethodAdapter(524288, mv);
            }
        }
        return wrappedMv;
    }
}
