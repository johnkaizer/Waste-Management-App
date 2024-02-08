package com.galaxiescoders.wastetracker.models;

public class ApplicationModel {
    String idNo;
    String name;
    String title;
    String date;

    public ApplicationModel() {
    }

    public ApplicationModel(String idNo, String name, String title, String date) {
        this.idNo = idNo;
        this.name = name;
        this.title = title;
        this.date = date;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
