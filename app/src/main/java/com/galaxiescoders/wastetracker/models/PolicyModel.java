package com.galaxiescoders.wastetracker.models;

public class PolicyModel {
    String title;
    String date;
    String content;
    String likes;

    public PolicyModel(String title, String date, String content, String likes) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.likes = likes;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }
}
