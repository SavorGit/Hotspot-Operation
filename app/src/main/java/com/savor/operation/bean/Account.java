package com.savor.operation.bean;

import java.io.Serializable;

/**
 * Created by hezd on 2017/11/6.
 */

public class Account implements Serializable {
    private String account;
    private String pwd;

    @Override
    public String toString() {
        return "Account{" +
                "account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account1 = (Account) o;

        if (account != null ? !account.equals(account1.account) : account1.account != null)
            return false;
        return pwd != null ? pwd.equals(account1.pwd) : account1.pwd == null;
    }

    @Override
    public int hashCode() {
        int result = account != null ? account.hashCode() : 0;
        result = 31 * result + (pwd != null ? pwd.hashCode() : 0);
        return result;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
