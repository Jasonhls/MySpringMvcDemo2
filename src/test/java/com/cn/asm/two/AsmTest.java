package com.cn.asm.two;

import com.cn.asm.two.asm.AccountAsm;
import com.cn.asm.two.asm.AddSecurityCheckClassAdapter;
import com.cn.asm.two.decorator.AccountWithSecurityCheck;
import com.cn.asm.two.proxy.SecurityProxyInvocationHandler;
import com.cn.subClassAndParentClass.testTwo.A;
import org.junit.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
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

    @Test
    public void testAsm() throws Exception{
        modifyAccountAsmClass();
        AccountAsm accountAsm = new AccountAsm();
        accountAsm.operation();
    }

    /**
     * 修改AccountAsm的class文件的内容
     * @throws Exception
     */
    public void modifyAccountAsmClass() throws Exception{
        ClassReader cr = new ClassReader("com.cn.asm.two.asm.AccountAsm");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classVisitor = new AddSecurityCheckClassAdapter(524288, cw);
        cr.accept(classVisitor, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("target\\test-classes\\com\\cn\\asm\\two\\asm\\AccountAsm.class");
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }
}
