package com.cn.utils;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: helisen
 * @create: 2020-12-08 10:40
 **/
public class RSAUtil {

    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";

    /**
     * 为RSA算法创建一个KeyPairGenerator对象
     * @param keySize
     * @return
     */
    public static Map<String, String> createKeys(int keySize) {
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        //初始化KeyPairGenerator对象，秘钥长度
        kpg.initialize(keySize);
        //生成密钥对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));
        //得到私钥
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));
        //map装载公钥和私钥
        Map<String, String> keyPairMap = new HashMap<>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        //返回map
        return keyPairMap;
    }

    /**
     *  获取公钥
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws Exception {
        //通过X509编码的key指令获取公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key = (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
        return key;
    }

    public static RSAPrivateKey getPrivateKey(String privateKey) throws Exception {
        //通过PKCS#8编码的key指令获得私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        return key;
    }

    /**
     * 公钥加密
     * @param data
     * @param publicKeyStr
     * @return
     */
    public static String publicEncrypt(String data, String publicKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPublicKey publicKey = getPublicKey(publicKeyStr);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE,
                    data.getBytes(CHARSET), publicKey.getModulus().bitLength()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥解密
     * @param data
     * @param privateKeyStr
     * @return
     */
    public static String privateDecrypt(String data, String privateKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPrivateKey privateKey = getPrivateKey(privateKeyStr);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    privateKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 私钥加密
     * @param data
     * @param privateKeyStr
     * @return
     */
    public static String privateEncrypt(String data, String privateKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPrivateKey privateKey = getPrivateKey(privateKeyStr);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE,
                    data.getBytes(CHARSET), privateKey.getModulus().bitLength()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * 公钥解密
     * @param data
     * @param publicKeyStr
     * @return
     */
    public static String publicDecrypt(String data, String publicKeyStr) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            RSAPublicKey publicKey = getPublicKey(publicKeyStr);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(data),
                    publicKey.getModulus().bitLength()), CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * rsa切割解码，ENCRYPT_MODE 加密数据， DECRYPT_MODE 解密数据
     * @param cipher
     * @param opmode
     * @param datas
     * @param keySize
     * @return
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        }else {
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = null;
        int offSet = 0;
        byte[] buff;
        int i = 0;
        byte[] resultDatas = null;
        try {
            out = new ByteArrayOutputStream();
            while(datas.length > offSet) {
                if(datas.length - offSet >maxBlock) {
                        buff = cipher.doFinal(datas, offSet, maxBlock);
                }else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            resultDatas = out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }finally {
            if(out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultDatas;
    }

    @Test
    public void test1() {
        Map<String, String> keyMap = RSAUtil.createKeys(1024);
        String publicKey = keyMap.get("publicKey");
        String privateKey = keyMap.get("privateKey");
        System.out.println("公钥为：" + publicKey);
        System.out.println("私钥为：" + privateKey);
        String str = "站在大明门前守卫的禁卫军，事先没有接到\n" + "有关的命令，但看到大批盛装的官员来临，也就\n" + "以为确系举行大典，因而未加询问。进大明门即\n" + "为皇城。文武百官看到端门午门之前气氛平静，\n" + "城楼上下也无朝会的迹象，既无几案，站队点名\n" + "的御史和御前侍卫“大汉将军”也不见踪影，不免\n"
                + "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("\r明文：\r\n" + str);
        System.out.println("\r明文长度：\r\n" + str.getBytes().length);
        String encodeData = RSAUtil.publicEncrypt(str, publicKey);
        System.out.println("密文：\r\n" + encodeData);
        String decodedData = RSAUtil.privateDecrypt(encodeData, privateKey);
        System.out.println("解密后文字：\r\n" + decodedData);
    }
}
