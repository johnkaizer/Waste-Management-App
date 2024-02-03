package com.galaxiescoders.wastetracker.models;

public class Payment {
    String userId;
    String paymentDate;
    String expiryDate;
    String paymentPackage;
    String paymentAmount;
    String houseNumber;
    String userName;
    String location;

    public Payment() {
    }

    public Payment(String userId, String paymentDate, String expiryDate, String paymentPackage, String paymentAmount, String houseNumber, String userName, String location) {
        this.userId = userId;
        this.paymentDate = paymentDate;
        this.expiryDate = expiryDate;
        this.paymentPackage = paymentPackage;
        this.paymentAmount = paymentAmount;
        this.houseNumber = houseNumber;
        this.userName = userName;
        this.location = location;
    }

    public String getUserUid() {
        return userId;
    }

    public void setUserUid(String userUid) {
        this.userId = userId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentPackage() {
        return paymentPackage;
    }

    public void setPaymentPackage(String paymentPackage) {
        this.paymentPackage = paymentPackage;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
}
