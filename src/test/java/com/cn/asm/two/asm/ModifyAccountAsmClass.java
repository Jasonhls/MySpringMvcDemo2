package com.cn.asm.two.asm;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-13 10:52
 **/
public class ModifyAccountAsmClass {
    /**
     * 修改AccountAsm的class文件的内容，下面的main方法执行一次就会修改AccountAsm.class文件一次，所以下面的方法执行n次，会被增加n次SecurityCheck方法
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        ClassReader cr = new ClassReader("com.cn.asm.two.asm.AccountAsm");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new AddSecurityCheckClassVisitor(458752, cw);
        cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("target\\test-classes\\com\\cn\\asm\\two\\asm\\AccountAsm.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }
}
