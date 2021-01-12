package com.cn.asm.two.decorator;

import com.cn.asm.two.Account;
import com.cn.asm.two.SecurityChecker;

/**
 * @description:
 * @author: helisen
 * @create: 2021-01-12 09:18
 **/
public class AccountWithSecurityCheck implements Account {
    private Account account;

    public AccountWithSecurityCheck(Account account) {
        this.account = account;
    }

    @Override
    public void operation() {
        SecurityChecker.checkSecurity();
        account.operation();
    }
}
