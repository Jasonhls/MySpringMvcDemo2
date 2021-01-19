package com.cn.classLoaderStudy.selfClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-15 14:55
 **/
public class DiskClassLoader extends ClassLoader{
    private String mLibPath;

    public DiskClassLoader(String mLibPath) {
        this.mLibPath = mLibPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = getFileName(name);
        File file = new File(mLibPath, name);
        try {
            FileInputStream is = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len = 0;
            while((len = is.read()) != -1) {
                bos.write(len);
            }
            byte[] data = bos.toByteArray();
            is.close();
            bos.close();
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.findClass(name);
    }

    private String getFileName(String name) {
        int index = name.lastIndexOf(".");
        if(index == -1) {
            return name + ".class";
        }else {
            return name.substring(index + 1) + ".class";
        }
    }
}
