package com.resonatestudios.pushupplus.controller;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class OpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbPushup.db";
    private static final String TABLE_CREATE =
            "CREATE TABLE HISTORY (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "AMOUNT INTEGER," +
                    "DURATION INTEGER," +
                    "DATE TEXT" +
                    ");";

    public OpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS HISTORY");
    }
}
