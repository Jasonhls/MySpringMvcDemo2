<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2020/7/20
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <title>使用jsencrypt执行OpenSSL的RSA加解密</title>
</head>
<!--引入jsencrypt.js-->
<%--这里使用jsencrypt.js实现公钥加密，私钥解密，如果想私钥解密，公钥加密，可以使用crypto.js--%>
<script src="https://cdn.bootcss.com/jsencrypt/3.0.0-rc.1/jsencrypt.min.js"></script>
<script type="text/javascript">
    //公钥
    var PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDXKFdjbiopXU83qXENge/isEfA0N6oMlgg5GssYMFZQmLHpIp8fyVJdvN7v/XcZIZ/YG3th7ubxBD2YV6nkSzhre1dhQDxBpIn+sq7jtLJtPYuOgFiXNi0ZLNPOQoKyd7Yy9MWZYRiSeQD4v4BoI1WG5dgm1YlczwRHYozjx/uTQIDAQAB";
    //私钥
    var PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANcoV2NuKildTzepcQ2B7+KwR8DQ3qgyWCDkayxgwVlCYsekinx/JUl283u/9dxkhn9gbe2Hu5vEEPZhXqeRLOGt7V2FAPEGkif6yruO0sm09i46AWJc2LRks085CgrJ3tjL0xZlhGJJ5APi/gGgjVYbl2CbViVzPBEdijOPH+5NAgMBAAECgYAecXXT+s16ZtqDnLUDXURNBfZRIPywPddcaBWGBPa2o38Iko6JyQ/EklEnTGUEVzD0qvdC6baw9pAT3HkhKph3385R4PV8nPbqBXM4IE0bWDvoJkFRG2oHM7zv+HKeBp/RBhNblDZyO+dQZ0fWvE/oJG1gEMGN2UljZ0fcTwgufQJBAPAzNuHAKnOGGocYjvED78bR647J2cL/82AZUogCOnpGojo7rFYKuf6WSdpNtnIjUoRZU/ao7gJkqeGlptZrRQ8CQQDlT25MQNCqhq7xaWnx5IFGT8IuZIXnJISs6vBeP5gt3Pbh3Les+P8Nq2qepLLCO2abpSz3rIJ7KO90yJhY1y7jAkB0K1DPvfDKDoGl9OqtBgphTgVu+ZCoDsHWKGkR/oHpwV+Uamfe9TEfP/BX9F3YtdyixdEl6m2yGp1O1J7r9NVhAkAfx+l2ggkpiS8X4E9BqjTMhONZwK7aQTJuMMWNmmzB9nz1Ar0mKQBYaaiGQjdQOiFXcwYPcYZIAX1mhyKuNWyXAkEA1LRVlRrquzBAC7URIQhWBbpjG0nAR4GYKInjFcAIT+dreRFbtfDu7+zUFFPZ/d6PKuxsGe7j1UZCuf0GRQlbTQ==";
    //使用公钥加密
    var encrypt = new JSEncrypt();
    //encrypt.setPrivateKey('-----BEGIN RSA PRIVATE KEY-----'+PRIVATE_KEY+'-----END RSA PRIVATE KEY-----');
    encrypt.setPublicKey('-----BEGIN PUBLIC KEY-----' + PUBLIC_KEY + '-----END PUBLIC KEY-----');
    var encrypted = encrypt.encrypt('AppBlog.CN');
    console.log('encrypt params: %o', encrypted);
    //使用私钥解密
    var decrypt = new JSEncrypt();
    //decrypt.setPublicKey('-----BEGIN PUBLIC KEY-----' + PUBLIC_KEY + '-----END PUBLIC KEY-----');
    decrypt.setPrivateKey('-----BEGIN RSA PRIVATE KEY-----'+PRIVATE_KEY+'-----END RSA PRIVATE KEY-----');
    var uncrypted = decrypt.decrypt(encrypted);
    console.log('decrypt result: %o', uncrypted);
</script>
</html>
