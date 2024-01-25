package com.galaxiescoders.wastetracker.models;

public class Zone {
    String title;
    String constituency;
    String wards;

    public Zone() {
    }

    public Zone(String title, String constituency, String wards) {
        this.title = title;
        this.constituency = constituency;
        this.wards = wards;
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
}
