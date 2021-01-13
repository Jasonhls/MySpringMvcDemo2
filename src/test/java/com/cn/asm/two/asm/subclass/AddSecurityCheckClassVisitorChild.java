package com.cn.asm.two.asm.subclass;

import com.cn.asm.two.asm.AddSecurityCheckMethodVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-12 17:08
 **/
public class AddSecurityCheckClassVisitorChild extends ClassVisitor {

    private String enhancedSuperName;


    public AddSecurityCheckClassVisitorChild(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //改变类命名，这里name本类为com/cn/asm/two/asm/AccountAsm, 父类名superName为java/lang/Object
        String enhancedName = name + "$EnhancedByASM";
        //改变父类，改成com/cn/asm/two/asm/AccountAsm
        enhancedSuperName = name;
        super.visit(version, access, enhancedName, signature, enhancedSuperName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor wrappedMv = mv;
        if(mv != null) {
            if(name.equals("operation")) {
                //AddSecurityCheckMethodVisitor是专门在方法之前插入SecurityCheck方法的，具体的实现方法就是重写MethodVisitor的visitCode方法
                wrappedMv = new AddSecurityCheckMethodVisitor(524288, mv);
            }
            if(name.equals("<init>")) {
                //ChangeToChildConstructorMethodVisitor是
                wrappedMv = new ChangeToChildConstructorMethodVisitor(524288, mv, enhancedSuperName);
            }
        }
        return wrappedMv;
    }
}
