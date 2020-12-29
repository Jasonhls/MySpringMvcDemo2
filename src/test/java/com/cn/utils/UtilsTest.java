package com.cn.utils;

import org.junit.Test;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-29 14:20
 **/
public class UtilsTest {
    @Test
    public void test1() throws Exception{
        String s = "pay_token=SYSTEM-DISTRIBUTOR-6eb5c4a2a66e40599824789f9985c0a9&pay_userId=4527391&pay_userName=13713974117&pay_order_no=FXS160879195752621936";
        String encrypt = AesUtils.encrypt(s);
        String encode = URLEncoder.encode(encrypt, "utf-8");
        //把换行符\r\n的编码值替换为空
        encode = encode.replaceAll(URLEncoder.encode("\r\n", "utf-8"), "");
        System.out.println("最终加密编码之后的结果：" + encode);

        String decode = URLDecoder.decode(encode, "utf-8");
        String decrypt = AesUtils.decrypt(decode);
        System.out.println("最终解码解码后的结果：" + decrypt);
    }
}
