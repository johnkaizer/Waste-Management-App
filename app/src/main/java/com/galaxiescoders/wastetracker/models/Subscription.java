package com.galaxiescoders.wastetracker.models;

public class Subscription {
    String amount;
    String title;
    String description;

    public Subscription() {
    }

    public Subscription(String amount, String title, String description) {
        this.amount = amount;
        this.title = title;
        this.description = description;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
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
}
