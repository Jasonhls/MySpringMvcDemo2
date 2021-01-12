package com.cn.asm;

import org.springframework.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-11 10:19
 **/
public class GeneratorClass {
    //自定义一个字节码文件
    public static void main(String[] args) throws Exception{
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        cw.visit(1, 0, "hls", null, "java.lang.Object", new String[]{""});
        cw.visitField(0, "name", "名称", null, "smith").visitEnd();
        cw.visitField(0, "age", "年龄", null, 20).visitEnd();
        //使cw类已经完成
        cw.visitEnd();
        //将cw转换成字节数组写到文件里面去
        byte[] data = cw.toByteArray();
        File file = new File("target\\test-classes\\com\\cn\\asm\\hls.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();

    }
}
