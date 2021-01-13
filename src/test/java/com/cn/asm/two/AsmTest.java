package com.cn.asm.two;

import com.cn.asm.two.asm.AccountAsm;
import com.cn.asm.two.asm.subclass.SecureAccountGenerator;
import com.cn.asm.two.decorator.AccountWithSecurityCheck;
import com.cn.asm.two.proxy.SecurityProxyInvocationHandler;
import org.junit.Test;

import java.lang.reflect.Proxy;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-11 18:22
 **/
public class AsmTest {

    /**
     * 测试使用 Decorator 模式
     */
    @Test
    public void testDecorator() {
        Account account = new AccountImpl();
        AccountWithSecurityCheck accountWithSecurityCheck = new AccountWithSecurityCheck(account);
        accountWithSecurityCheck.operation();
    }

    /**
     * 测试使用Proxy
     */
    @Test
    public void testProxy() {
        Account ai = new AccountImpl();
        SecurityProxyInvocationHandler securityProxyInvocationHandler = new SecurityProxyInvocationHandler(ai);
        Account account = (Account) Proxy.newProxyInstance(Account.class.getClassLoader(),
                new Class[]{Account.class},
                securityProxyInvocationHandler
        );
        account.operation();
    }

    /**
     * 使用ASM修改本类的Class文件内容，下面的test必须先去执行一次ModifyAccountAsmClass的main方法
     * 每次maven clean都会清空class文件
     */
    @Test
    public void testAsm(){
        AccountAsm accountAsm = new AccountAsm();
        accountAsm.operation();
    }

    @Test
    public void testAsmSubclass() throws Exception{
        SecureAccountGenerator secureAccountGenerator = new SecureAccountGenerator();
        AccountAsm accountAsm = secureAccountGenerator.generateSecureAccount();
        accountAsm.operation();
    }


}
