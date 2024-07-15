package com.cn.utils;

import org.junit.Test;
import org.springframework.util.Base64Utils;

import java.io.IOException;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-08 09:27
 **/
public class BaseUtil {
    /**
     * BASE64解密
     * @param key
     * @return
     * @throws IOException
     */
    public static byte[] decryBASE64(String key) throws IOException {
        return Base64Utils.decodeFromString(key);
    }

    /**
     * BASE64加密
     * @param key
     * @return
     */
    public static String encryptBASE64(byte[] key) {
        return Base64Utils.encodeToString(key);
    }

    @Test
    public void test1() throws Exception {
        String key = "s8df92faf1";
        String s = BaseUtil.encryptBASE64(key.getBytes("utf-8"));
        byte[] bytes = BaseUtil.decryBASE64(s);
        String result = new String(bytes, "utf-8");
        System.out.println(result);
    }
}
