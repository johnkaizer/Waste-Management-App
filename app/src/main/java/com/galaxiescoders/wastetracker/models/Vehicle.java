package com.galaxiescoders.wastetracker.models;

public class Vehicle {
    String regNum;
    String engineNum;
    String YOM;
    String Model;
    String status;
    String staffId;

    public Vehicle() {
    }

    public Vehicle( String regNum, String engineNum, String YOM, String model, String status, String staffId) {
        this.regNum = regNum;
        this.engineNum = engineNum;
        this.YOM = YOM;
        Model = model;
        this.status = status;
        this.staffId = staffId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
