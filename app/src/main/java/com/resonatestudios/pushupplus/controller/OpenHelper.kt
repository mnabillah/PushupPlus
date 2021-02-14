package com.resonatestudios.pushupplus.controller

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class OpenHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS HISTORY")
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "dbPushup.db"
        private const val TABLE_CREATE = "CREATE TABLE HISTORY (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AMOUNT INTEGER," +
                "DURATION INTEGER," +
                "DATE TEXT" +
                ");"
    }
}