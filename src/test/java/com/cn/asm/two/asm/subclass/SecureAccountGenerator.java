package com.cn.asm.two.asm.subclass;

import com.cn.asm.two.asm.AccountAsm;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-12 17:24
 **/
public class SecureAccountGenerator {

    private static AccountGeneratorClassLoader classLoader = new AccountGeneratorClassLoader();

    private static Class secureAccountClass;

    public AccountAsm generateSecureAccount() throws Exception{
        if(null == secureAccountClass) {
            ClassReader cr = new ClassReader("com.cn.asm.two.asm.AccountAsm");
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            ClassVisitor classVisitor = new AddSecurityCheckClassVisitorChild(524288, cw);
            cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
            byte[] data = cw.toByteArray();
            secureAccountClass = classLoader.defineClassFromClassFile("com.cn.asm.two.asm.AccountAsm$EnhancedByASM", data);
        }
        return (AccountAsm)secureAccountClass.newInstance();
    }

    private static class AccountGeneratorClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className, byte[] classFile) {
            return defineClass(className, classFile, 0, classFile.length);
        }
    }
}
