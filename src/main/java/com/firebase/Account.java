package com.firebase;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Account {
    private String bankNameFirebase;
    private String bankUserName;
    private String bankPassword;

    public Account() {
    }

    public Account(String bankNameFirebase, String bankUserName, String bankPassword) {
        this.bankNameFirebase = bankNameFirebase;
        this.bankUserName = bankUserName;
        this.bankPassword = bankPassword;
    }

    public String getBankName() {
        return bankNameFirebase;
    }

    public void setBankName(String bankNameFirebase) {
        this.bankNameFirebase = bankNameFirebase;
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
