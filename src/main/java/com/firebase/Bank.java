package com.firebase;

public class Bank {
    private String bankName;
    private String bankURL;
//    private  String bankFirebaseId;

    public Bank() {
    }

    public Bank(String bankName, String bankURL) {
        this.bankName = bankName;
        this.bankURL = bankURL;
//        this.bankFirebaseId = bankFirebaseId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankURL() {
        return bankURL;
    }

    public void setBankURL(String bankURL) {
        this.bankURL = bankURL;
    }
//
//    public String getBankFirebaseId() {
//        return bankFirebaseId;
//    }
//
//    public void setBankFirebaseId(String bankFirebaseId) {
//        this.bankFirebaseId = bankFirebaseId;
//    }
}
