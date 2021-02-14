package com.resonatestudios.pushupplus.model;

import java.util.Date;

public class HistoryItem {
    private int id;
    private int amount;
    private long duration;
    private Date date;

    public HistoryItem(int id, int amount, long duration, Date date) {
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

    public long getDuration() {
        return duration;
    }

    public Date getDate() {
        return date;
    }
}
