package com.resonatestudios.pushupplus.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resonatestudios.pushupplus.model.HistoryItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class DbHistory {
    public static final String TABLE_NAME = "HISTORY";
    public static final String[] collumns = {"ID", "AMOUNT", "DURATION", "DATE"};
    private final OpenHelper dbHelper;
    private SQLiteDatabase db;

    public DbHistory(Context context) {
        dbHelper = new OpenHelper(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public ArrayList<HistoryItem> getAll() throws ParseException {
        return getAll(collumns[0]);
    }

    public ArrayList<HistoryItem> getAll(String sortBy) throws ParseException {
        Cursor cursor;
        ArrayList<HistoryItem> historyItems = new ArrayList<>();

        cursor = db.query(TABLE_NAME, collumns, null, null, null, null, sortBy + " ASC");

        if (cursor.moveToFirst()) {
            historyItems.add(new HistoryItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getLong(2),
                    DateFormat.getDateInstance().parse(cursor.getString(3))
            ));
            while (cursor.moveToNext()) {
                historyItems.add(new HistoryItem(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getLong(2),
                        DateFormat.getDateInstance().parse(cursor.getString(3))
                ));
            }
        }

        cursor.close();

        return historyItems;
    }

    public boolean insert(int amount, long duration, Date date) {
        ContentValues newValue = new ContentValues();
        newValue.put("AMOUNT", amount);
        newValue.put("DURATION", duration);
        newValue.put("DATE", DateFormat.getDateInstance().format(date));
        return db.insert(TABLE_NAME, null, newValue) > 0;
    }

    public boolean deleteAll() {
        return db.delete(TABLE_NAME, "ID>-1", null) > 0;
    }
}
