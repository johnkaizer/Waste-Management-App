package com.galaxiescoders.wastetracker.models;

public class Vehicle {
    String ImageUrl;
    String regNum;
    String engineNum;
    String YOS;
    String YOM;
    String Model;
    String staffId;

    public Vehicle() {
    }

    public Vehicle(String imageUrl, String regNum, String engineNum, String YOS, String YOM, String model, String staffId) {
        ImageUrl = imageUrl;
        this.regNum = regNum;
        this.engineNum = engineNum;
        this.YOS = YOS;
        this.YOM = YOM;
        Model = model;
        this.staffId = staffId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getRegNum() {
        return regNum;
    }

    public void setRegNum(String regNum) {
        this.regNum = regNum;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public String getYOS() {
        return YOS;
    }

    public void setYOS(String YOS) {
        this.YOS = YOS;
    }

    public String getYOM() {
        return YOM;
    }

    public void setYOM(String YOM) {
        this.YOM = YOM;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
