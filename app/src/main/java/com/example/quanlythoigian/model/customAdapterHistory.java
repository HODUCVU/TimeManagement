package com.example.quanlythoigian.model;


import java.io.Serializable;

public class customAdapterHistory implements Serializable {
    private String day;
    private String time;
    private String event;
    private int completed;

    public customAdapterHistory(String day, String time, String event, int completed) {
        this.day = day;
        this.time = time;
        this.event = event;
        this.completed = completed;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public boolean getCompleted() {
        return completed != 0;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }
}
