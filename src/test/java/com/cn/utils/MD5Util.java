package com.cn.utils;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.util.DigestUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * @description: MD5加密方式
 * @author: helisen
 * @create: 2020-12-08 09:33
 **/
public class MD5Util {
    public static final String KEY_MD5 = "MD5";

    public static byte[] encryptMD5One(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    public static byte[] encryptMD5Two(byte[] data) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        return md5.digest(data);
    }

    //使用java的jar自带的工具
    public static byte[] encryptMD5ByJavaJar(String key) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        return md5.digest(key.getBytes("utf-8"));
    }

    //使用spring自带的工具
    public static byte[] encryptMD5BySpring(String key) throws Exception {
        return DigestUtils.md5Digest(key.getBytes("utf-8"));
    }


    //使用java的jar自带的工具
    public static String encryptMD5AsHexByJavaJar(String key) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        byte[] digest = md5.digest(key.getBytes("utf-8"));
        String md5Code = new BigInteger(1, digest).toString(16);
        for (int i = 0; i < 32 - md5Code.length(); i++) {
            md5Code = "0" + md5Code;
        }
        return md5Code;
    }

    //使用spring自带的工具
    public static String encryptMD5AsHexBySpring(String key) throws Exception {
        return DigestUtils.md5DigestAsHex(key.getBytes("utf-8"));
    }


    public static String encryptMD5AsHexBySpringWithAppend(String key) throws Exception {
        StringBuilder sb = new StringBuilder("hls");
        StringBuilder hls = DigestUtils.appendMd5DigestAsHex(key.getBytes("utf-8"), sb);
        return hls.toString();
    }


    /**
     * byte[]字节数组转换成16进制字符串
     * @param arr
     * @return
     */
    public static String hex(byte[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(Integer.toHexString((arr[i] & 0XFF) | 0X100).substring(1, 3));
        }
        return sb.toString();
    }

    /**
     * 获取十六进制字符串形式的MD5摘要
     * @param key
     * @return
     * @throws Exception
     */
    public static String md5Hex(String key) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        byte[] digest = md5.digest(key.getBytes());
        return hex(digest);
    }

    /**
     * 加盐MD5
     * @param key
     * @return
     * @throws Exception
     */
    public static String getSaltMD5(String key) throws Exception {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        if(len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        //生成最终的加密盐
        String salt = sb.toString();
        //7002087625619435，生成的盐为16位随机数
        System.out.println("生成最终的盐为：" + salt);
        key = md5Hex(key + salt);
        //key为需要加密的字符串拼接salt，然后经过md5Hex方法得到的字符串
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = key.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = key.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 检查加盐后是否与原文一致
     * @param key  原需要进行加密的字符串
     * @param md5Str   加盐加密后的值
     * @return
     * @throws Exception
     */
    public static boolean getSaltVerifyMD5(String key, String md5Str) throws Exception {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i+=3) {
            cs1[i / 3 * 2] = md5Str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5Str.charAt(i + 2);
            cs2[i / 3] = md5Str.charAt(i + 1);
        }
        //经过上面这样，cs2就为salt，cs1为需要加密的字符串拼接salt，然后经过md5Hex方法得到的字符串，
        // 所以String.valueOf(cs1)当然等于md5Hex(key + salt)，这里的key为需要加密的字符串
        String salt = new String(cs2);
        return md5Hex(key + salt).equals(String.valueOf(cs1));
    }


    /**
     * md5加密
     * @param key
     * @return
     */
    public static String md5(String key) {
        return encrypt(key, "md5");
    }

    /**
     * sha-1加密
     * @param key
     * @return
     */
    public static String sha(String key) {
        return encrypt(key, "sha-1");
    }

    /**
     * 加密
     * @param key
     * @param algorithmName
     * @return
     */
    public static String encrypt(String key, String algorithmName) {
        if(StringUtils.isBlank(key)) {
            throw new IllegalArgumentException("请输入要加密的内容");
        }
        if(StringUtils.isBlank(algorithmName)) {
            algorithmName = "md5";
        }
        try {
            MessageDigest m = MessageDigest.getInstance(algorithmName);
            m.update(key.getBytes("utf-8"));
            byte[] s = m.digest();
            return hex(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * md5和sha-1混合加密
     * @param key
     * @return
     */
    public static String md5AndSha(String key) {
        return sha(md5(key));
    }


    /**
     * 生成含有随机盐的密码，使用的是md5和sha-1的混合加密，然后再加盐
     * @param key
     * @return
     * @throws Exception
     */
    public static String getSaltMD5AndSha(String key) throws Exception {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(16);
        sb.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sb.length();
        if(len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        //生成最终的加密盐
        String salt = sb.toString();
        //8664942281131539，生成的盐为16位随机数
        System.out.println("生成最终的盐为：" + salt);
        //使用md5和sha混合加密
        key = md5AndSha(key + salt);
        //65e8f618cd063d13828967426c756f167ac1e359，为40位，md5和sha混合加密得到40位的值
        System.out.println("md5AndSha加密后的值：" + key);
        //key为需要加密的字符串拼接salt，然后经过md5Hex方法得到的字符串
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = key.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = key.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }

    /**
     * 验证加盐后是否与原密码一致
     * @param key  原密码
     * @param md5Str  使用的是md5和sha-1的混合加密，然后再加盐后生成的字符串
     * @return
     * @throws Exception
     */
    public static boolean getSaltVerifyMD5AndSha(String key, String md5Str) throws Exception {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i+=3) {
            cs1[i / 3 * 2] = md5Str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5Str.charAt(i + 2);
            cs2[i / 3] = md5Str.charAt(i + 1);
        }
        //经过上面这样，cs2就为salt，cs1为需要加密的字符串拼接salt，然后经过md5Hex方法得到的字符串，
        // 所以String.valueOf(cs1)当然等于md5Hex(key + salt)，这里的key为需要加密的字符串
        String salt = new String(cs2);
        String s = md5AndSha(key + salt);
        //加密密码去掉最后8位数
        s = s.substring(0, s.length() - 8);
        return s.equals(String.valueOf(cs1));
    }

    @Test
    public void test5() throws Exception {
        String key = "hello";
        String  s = MD5Util.getSaltMD5AndSha(key);
        //d8e76b26434f391c4a12352658f51db15039414859936299，48位，md5和sha加密后再加盐得到48位的值。
        System.out.println(s);
        System.out.println(MD5Util.getSaltVerifyMD5AndSha(key, s));
    }




    @Test
    public void test4() throws Exception {
        String key = "hello";
        String saltMD5 = MD5Util.getSaltMD5(key);
        //27ea06700c2480ef85e7f762924f51e6c612599846a3545c，48位
        System.out.println(saltMD5);
        System.out.println(MD5Util.getSaltVerifyMD5(key, saltMD5));
    }




    @Test
    public void test1() throws Exception {
        String key = "hello";
        String resultOne = hex(MD5Util.encryptMD5ByJavaJar(key));
        String resultTwo = hex(MD5Util.encryptMD5BySpring(key));

        //5d41402abc4b2a76b9719d911017c592
        System.out.println(resultOne);
        System.out.println(resultTwo);
    }

    @Test
    public void test2() throws Exception {
        String key = "hello";
        String resultOne = MD5Util.encryptMD5AsHexByJavaJar(key);
        String resultTwo = MD5Util.encryptMD5AsHexBySpring(key);

        //加密后是32的字符串，5d41402abc4b2a76b9719d911017c592
        System.out.println(resultOne);
        System.out.println(resultTwo);
    }

    @Test
    public void test3() throws Exception {
        String key = "abc";

        String s1 = MD5Util.encryptMD5AsHexBySpring(key);
        //900150983cd24fb0d6963f7d28e17f72
        System.out.println(s1);

        String s = MD5Util.encryptMD5AsHexBySpringWithAppend(key);
        //hls900150983cd24fb0d6963f7d28e17f72
        System.out.println(s);
    }
}
