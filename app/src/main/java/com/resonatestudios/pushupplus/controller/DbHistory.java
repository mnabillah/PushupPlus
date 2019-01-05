package com.resonatestudios.pushupplus.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.resonatestudios.pushupplus.adapter.HistoryListAdapter;
import com.resonatestudios.pushupplus.model.HistoryItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DbHistory {
    public static final String TABLE_NAME = "HISTORY";
    private final OpenHelper dbHelper;
    // kolom yang diambil
    private String[] collumns = {"ID", "AMOUNT", "DURATION", "DATE"};
    private SQLiteDatabase db;

    HistoryListAdapter historyListAdapter;

    public DbHistory(Context context, HistoryListAdapter historyListAdapter) {
        dbHelper = new OpenHelper(context);
        this.historyListAdapter = historyListAdapter;
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public ArrayList<HistoryItem> getAll() throws ParseException {
        Cursor cursor;
        ArrayList<HistoryItem> historyItems = new ArrayList<>();

        cursor = db.query(TABLE_NAME, collumns, null, null, null, null, "ID DESC");

        if (cursor.moveToFirst()) {
            historyItems.add(new HistoryItem(
                    cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getInt(2),
                    new SimpleDateFormat("yyyy/MM/dd").parse(cursor.getString(3))
            ));
            while (cursor.moveToNext()) {
                historyItems.add(new HistoryItem(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getInt(2),
                        new SimpleDateFormat("yyyy/MM/dd").parse(cursor.getString(3))
                ));
            }
        }

        cursor.close();

        return historyItems;
    }

    public boolean insert(HistoryItem historyItem) {
        ContentValues newValue = new ContentValues();
        newValue.put("AMOUNT", historyItem.getAmount());
        newValue.put("DURATION", historyItem.getDuration());
        newValue.put("DATE", String.valueOf(historyItem.getDate()));
        return db.insert(TABLE_NAME, null, newValue) > 0;
    }

    public boolean deleteAll() {
        return db.delete(TABLE_NAME, "ID>-1", null) > 0;
    }
}
