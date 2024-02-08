package com.galaxiescoders.wastetracker.models;

public class TenderModel {
    String title;
    String description;
    String amount;
    String deadline;

    public TenderModel() {
    }

    public TenderModel(String title, String description, String amount, String deadline) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
