package com.galaxiescoders.wastetracker.models;

public class Tools {
    String ImageUrl;
    String name;
    String YOB;
    String status;
    String staffId;

    public Tools() {
    }

    public Tools(String imageUrl, String name, String YOB, String status, String staffId) {
        ImageUrl = imageUrl;
        this.name = name;
        this.YOB = YOB;
        this.status = status;
        this.staffId = staffId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYOB() {
        return YOB;
    }

    public void setYOB(String YOB) {
        this.YOB = YOB;
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
