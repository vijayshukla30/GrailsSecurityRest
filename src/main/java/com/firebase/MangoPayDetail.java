package com.firebase;

public class MangoPayDetail {
    private String mangoPayId;
    private String mangoPayWalletId;
    private String mangoPayBankccountId;
    private String mangoPayMandateId;

    public MangoPayDetail() {
    }

    public MangoPayDetail(String mangoPayId, String mangoPayWalletId, String mangoPayBankccountId, String mangoPayMandateId) {
        this.mangoPayId = mangoPayId;
        this.mangoPayWalletId = mangoPayWalletId;
        this.mangoPayBankccountId = mangoPayBankccountId;
        this.mangoPayMandateId = mangoPayMandateId;
    }

    public String getMangoPayId() {
        return mangoPayId;
    }

    public void setMangoPayId(String mangoPayId) {
        this.mangoPayId = mangoPayId;
    }

    public String getMangoPayWalletId() {
        return mangoPayWalletId;
    }

    public void setMangoPayWalletId(String mangoPayWalletId) {
        this.mangoPayWalletId = mangoPayWalletId;
    }

    public String getMangoPayBankccountId() {
        return mangoPayBankccountId;
    }

    public void setMangoPayBankccountId(String mangoPayBankccountId) {
        this.mangoPayBankccountId = mangoPayBankccountId;
    }

    public String getMangoPayMandateId() {
        return mangoPayMandateId;
    }

    public void setMangoPayMandateId(String mangoPayMandateId) {
        this.mangoPayMandateId = mangoPayMandateId;
    }
}
