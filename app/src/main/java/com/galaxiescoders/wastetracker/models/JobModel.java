package com.galaxiescoders.wastetracker.models;

public class JobModel {
    String title;
    String description;
    String salary;
    String deadline;

    public JobModel() {
    }

    public JobModel(String title, String description, String salary, String deadline) {
        this.title = title;
        this.description = description;
        this.salary = salary;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
