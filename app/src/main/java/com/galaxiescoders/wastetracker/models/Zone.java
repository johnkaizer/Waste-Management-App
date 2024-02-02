package com.galaxiescoders.wastetracker.models;

public class Zone {
    String title;
    String constituency;
    String wards;
    String status;
    String staffId;

    public Zone() {
    }

    public Zone(String title, String constituency, String wards, String status, String staffId) {
        this.title = title;
        this.constituency = constituency;
        this.wards = wards;
        this.status = status;
        this.staffId = staffId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getWards() {
        return wards;
    }

    public void setWards(String wards) {
        this.wards = wards;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
