package com.example.quanlythoigian.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class customAdapterCalender implements Serializable {
    private int id;
    private String time;
    private String event;
    private int completed;
    public customAdapterCalender(int id, String time, String event, int completed) {
        this.id = id;
        this.time = time;
        this.event = event;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isCompleted() {
        return completed != 0;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.id+ ")" + " " + this.time + "\n\t" + this.event;
    }
}
