package com.galaxiescoders.wastetracker.models;

import java.util.Date;

import java.util.Date;

import java.util.Date;

public class ComplaintModel {
    private String complaintId; // Add complaintId field
    private String userId;
    private String location;
    private String complaint;
    private String complaintDate;
    private String status;
    private String phoneNumber;

    // Constructors
    public ComplaintModel() {
    }

    public ComplaintModel(String complaintId, String userId, String location, String complaint, String complaintDate, String status, String phoneNumber) {
        this.complaintId = complaintId;
        this.userId = userId;
        this.location = location;
        this.complaint = complaint;
        this.complaintDate = complaintDate;
        this.status = status;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(String complaintDate) {
        this.complaintDate = complaintDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}


