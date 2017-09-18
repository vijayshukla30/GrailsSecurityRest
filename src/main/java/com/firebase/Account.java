package com.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Account {
    private String bankName;
    private String bankUserName;
    private String bankPassword;

    public Account() {
    }

    public Account(String bankName, String bankUserName, String bankPassword) {
        this.bankName = bankName;
        this.bankUserName = bankUserName;
        this.bankPassword = bankPassword;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankUserName() {
        return bankUserName;
    }

    public void setBankUserName(String bankUserName) {
        this.bankUserName = bankUserName;
    }

    public String getBankPassword() {
        return bankPassword;
    }

    public void setBankPassword(String bankPassword) {
        this.bankPassword = bankPassword;
    }
}
