package com.firebase;

public class DirectDebitMandate {
    private String owner;
    private String addressLine1;
    private String city;
    private String region;
    private String postCode;
    private String country;
    private String iban;
    private String bic;

    public DirectDebitMandate() {
    }

    public DirectDebitMandate(String owner, String addressLine1, String city, String region, String postCode, String country, String iban, String bic) {
        this.owner = owner;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.region = region;
        this.postCode = postCode;
        this.country = country;
        this.iban = iban;
        this.bic = bic;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}