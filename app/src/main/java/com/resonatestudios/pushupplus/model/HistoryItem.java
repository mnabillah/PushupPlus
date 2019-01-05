package com.resonatestudios.pushupplus.model;

import java.util.Date;

public class HistoryItem {
    private int id;
    private int amount;
    private int duration;
    private Date date;

    public HistoryItem(int id, int amount, int duration, Date date) {
        this.id = id;
        this.amount = amount;
        this.duration = duration;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public int getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }
}
